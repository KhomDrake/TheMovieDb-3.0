package com.vlv.bondsmith

import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.ResponseLiveData
import com.vlv.bondsmith.data.Response
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.toDataResult
import com.vlv.bondsmith.log.LogHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

private val bondsmithScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

class Bondsmith<Data>(
    private val scope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO),
    private val tag: String = ""
) {

    private val lock = object {}

    private var config: Config<Data> = Config(tag)

    private val _responseLiveData = MutableResponseLiveData<Data>()

    private val _responseStateFlow = MutableResponseStateFlow<Data>(Response())

    val responseLiveData: ResponseLiveData<Data>
        get() = _responseLiveData

    val responseStateFlow: ResponseStateFlow<Data>
        get() = _responseStateFlow
            .shareIn(
                bondsmithScope,
                SharingStarted.WhileSubscribed(),
                replay = 0
            )

    fun config(func: Config<Data>.() -> Unit) = apply {
        config = Config<Data>(tag).apply(func)
    }

    fun config(newConfig: Config<Data>) = apply {
        config = newConfig
    }

    fun logInfo(info: String) {
        LogHandler.logInfo(tag, info)
    }

    fun execute() = synchronized(lock) {
        scope.launch {
            runCatching {
                createExecution().start()
            }.onFailure {
                _responseStateFlow.emitError(it)
                _responseLiveData.postError(it)
            }
        }
        return@synchronized this
    }

    private fun createExecution() = scope.launch(Dispatchers.IO) {
        logInfo("[Execution] creating execution")
        flow {
            config.execute(
                this,
                this@Bondsmith
            )
        }
            .onEach {
                _responseLiveData.postValue(it.toDataResult())
            }
            .catch {
                _responseStateFlow.emitError(it)
            }
            .collect {
                _responseStateFlow.emit(it)
            }

    }

}

fun <T>bondsmith(
    tag: String = "",
    scope: CoroutineScope = bondsmithScope
) : Bondsmith<T> {
    return Bondsmith(scope, tag)
}
