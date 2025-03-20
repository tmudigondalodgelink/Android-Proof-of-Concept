package com.example.domainmodule.errors

sealed class IdentificationError(message: String) : Throwable(message) {
    class EmptyIdentifier(private val forType: String) : IdentificationError(forType)
}