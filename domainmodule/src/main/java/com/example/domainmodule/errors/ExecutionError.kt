package com.example.domainmodule.errors

sealed class ExecutionError(message: String) : Throwable(message) {
    class WithMessage(customMessage: String) : ExecutionError(customMessage)
}