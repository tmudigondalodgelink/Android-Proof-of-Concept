package com.example.domainmodule.errors

sealed class ExecutionError(message: String) : Throwable(message) {
    class WithMessage(private val customMessage: String) : ExecutionError(customMessage)
}