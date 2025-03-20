package com.example.domainmodule.errors

sealed class AuthenticationError(message: String) : Throwable(message) {
    class CredentialsParsingError(private val credentialsParsingError: CredentialsParsingError) : AuthenticationError(credentialsParsingError.message ?: "")
    class DataError(private val dataError: DataError) : AuthenticationError(dataError.message ?: "")
    class UnexpectedError(private val errorMessage: String) : AuthenticationError(errorMessage)

    val errorDescription: String?
        get() = message
}