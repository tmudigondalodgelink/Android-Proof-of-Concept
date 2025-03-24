package com.example.datamodule.repositories

import com.example.domainmodule.repositoryinterfaces.ITestRepository
import com.example.domainmodule.repositoryinterfaces.SomeException
import com.example.domainmodule.utilities.FlowResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TestRepository: ITestRepository {
    override fun getTestFlow(): Flow<FlowResult<String, SomeException>> = flow {
        println("Current thread: ${Thread.currentThread().name}")

        emit(FlowResult.Success("The string that came from the data layer"))
//        emit(FlowResult.Failure(SomeException(SomeError.SOME_ERROR)))
    }
}