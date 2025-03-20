package com.example.domainmodule.errors

sealed class CredentialsParsingError(message: String) : Throwable(message) {
    class InvalidEmail : CredentialsParsingError("Invalid email format")
    class InvalidPassword : CredentialsParsingError("The password should be between 2 and 25 characters long")
}