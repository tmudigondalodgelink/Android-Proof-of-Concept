package com.example.domainmodule.errors

sealed class ParsingError(message: String) : Throwable(message) {
    class ParsingFailed(private val customMessage: String) : ParsingError(customMessage)
}