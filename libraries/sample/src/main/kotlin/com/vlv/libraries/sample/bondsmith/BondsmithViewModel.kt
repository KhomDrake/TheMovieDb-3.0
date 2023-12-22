package com.vlv.libraries.sample.bondsmith

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.arch.toolkit.livedata.response.MutableResponseLiveData
import br.com.arch.toolkit.livedata.response.SwapResponseLiveData
import com.vlv.bondsmith.data.flow.MutableResponseStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BondsmithViewModel(
    private val repository: BondsmithRepository
) : ViewModel() {

    val state = MutableResponseStateFlow<String>()
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
                    state.emit(it)
                }
        }
    }

}