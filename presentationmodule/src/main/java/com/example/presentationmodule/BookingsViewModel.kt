package com.example.architecturepoc2

import androidx.lifecycle.ViewModel
import com.example.domainmodule.usecases.ITestUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    }
}
