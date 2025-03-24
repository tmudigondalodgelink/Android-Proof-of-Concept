package com.example.presentationmodule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domainmodule.usecases.ITestUseCase
import com.example.domainmodule.utilities.FlowResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookingsViewModel @Inject constructor(
    private val testUseCase: ITestUseCase
) : ViewModel() {
    private val _counter = MutableStateFlow(0)
    val counter: StateFlow<Int> = _counter.asStateFlow()

    fun increment() {
        testUseCase.printLog()

        _counter.value += 1

        testUseCase.execute()
            .collectInScope(viewModelScope) { result ->
                when (result) {
                    is FlowResult.Success -> println("Something happened ${result.value}")
                    is FlowResult.Failure -> println("Error occurred: ${result.error}")
                }

                println("Current thread: ${Thread.currentThread().name}")
            }
    }
}


fun <T> Flow<T>.collectInScope(scope: CoroutineScope, collector: (T) -> Unit) {
    scope.launch {
        collect { collector(it) }
    }
}