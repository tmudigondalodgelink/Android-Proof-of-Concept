package com.example.domainmodule.errors

sealed class AuthenticationError(message: String) : Throwable(message) {
    class CredentialsParsingError(credentialsParsingError: com.example.domainmodule.errors.CredentialsParsingError) : AuthenticationError(credentialsParsingError.message ?: "")
    class DataError(dataError: com.example.domainmodule.errors.DataError) : AuthenticationError(dataError.message ?: "")
    class UnexpectedError(errorMessage: String) : AuthenticationError(errorMessage)

    val errorDescription: String?
        get() = message
}