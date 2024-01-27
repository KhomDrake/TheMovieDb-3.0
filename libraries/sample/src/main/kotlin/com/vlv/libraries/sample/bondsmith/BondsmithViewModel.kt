package com.vlv.libraries.sample.bondsmith

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arch.toolkit.livedata.response.SwapResponseLiveData
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import com.vlv.bondsmith.data.flow.ResponseStateFlow
import com.vlv.bondsmith.data.flow.asResponseStateFlow
import kotlinx.coroutines.launch

class BondsmithViewModel(
    private val repository: BondsmithRepository
) : ViewModel() {

    val responseStateFlow: ResponseStateFlow<String>
        get() = mutableResponseState.asResponseStateFlow()
    val mutableResponseState = MutableResponseStateFlow<String>()
    val liveData = SwapResponseLiveData<String>()

    fun responseLiveData(
        error: Boolean = false,
        withCache: Boolean = true
    ) {
        viewModelScope.launch {
            repository.default(error, withCache).responseLiveData
                .map {
                    "Next Int $it"
                }
                .mapError {
                    Throwable()
                }
                .apply {
                    liveData.swapSource(this)
                }
        }
    }


    fun flow(
        error: Boolean = false,
        withCache: Boolean = true
    ) {
        viewModelScope.launch {
            repository.default(error, withCache)
                .responseStateFlow
                .mapData {
                    "Next Int ${it.toString()}"
                }
                .collect {
                    mutableResponseState.emit(it)
                }
        }
    }

}