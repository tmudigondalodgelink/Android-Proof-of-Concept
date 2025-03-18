package com.example.domainmodule.usecases

import android.util.Log

interface  ITestUseCase {
    fun printLog()
}

class TestUseCase: ITestUseCase {
    override fun printLog() {
        println("Hello Dolly")
    }
}