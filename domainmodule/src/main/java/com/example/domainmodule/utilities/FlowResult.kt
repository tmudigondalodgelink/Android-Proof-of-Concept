package com.example.domainmodule.utilities

sealed class FlowResult<T, E: Throwable> {
    data class Success<T, E: Throwable>(val value: T) : FlowResult<T, E>()
    data class Failure<T, E: Throwable>(val error: E) : FlowResult<T, E>()
}