package com.example.domainmodule.usecases

import com.example.domainmodule.repositoryinterfaces.ITestRepository
import com.example.domainmodule.repositoryinterfaces.SomeError
import com.example.domainmodule.repositoryinterfaces.SomeException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.withContext

interface  ITestUseCase {
    fun printLog()
    fun execute(): Flow<FlowResult<String, SomeException>>
}

class TestUseCase(
    private val testRepository: ITestRepository
): ITestUseCase {
    override fun printLog() {
        println("Hello Dolly")
    }

    override fun execute(): Flow<FlowResult<String, SomeException>> = flow<FlowResult<String, SomeException>> {


        val signInResult = testRepository.getTestFlow()
            .first()

        when (signInResult) {
            is FlowResult.Success -> emit(FlowResult.Success(signInResult.value))
            is FlowResult.Failure -> emit(FlowResult.Failure(SomeException(SomeError.SOME_ERROR)))
        }
    }.flowOn(Dispatchers.IO).take(1)
}

sealed class FlowResult<T, E: Throwable> {
    data class Success<T, E: Throwable>(val value: T) : FlowResult<T, E>()
    data class Failure<T, E: Throwable>(val error: E) : FlowResult<T, E>()
}