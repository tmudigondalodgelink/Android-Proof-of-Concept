package com.example.domainmodule.repositoryinterfaces

import com.example.domainmodule.usecases.FlowResult
import kotlinx.coroutines.flow.Flow

interface ITestRepository {
    fun getTestFlow(): Flow<FlowResult<String, SomeException>>
}

enum class SomeError(val message: String) {
    SOME_ERROR("SOME ERROR")
}

class SomeException(private val error: SomeError): Throwable(error.message)