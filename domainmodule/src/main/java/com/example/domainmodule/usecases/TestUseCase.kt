package com.example.domainmodule.usecases

import com.example.domainmodule.repositoryinterfaces.ITestRepository

interface  ITestUseCase {
    fun printLog()
}

class TestUseCase(
    private val testRepository: ITestRepository
): ITestUseCase {
    override fun printLog() {
        println("Hello Dolly")
    }
}