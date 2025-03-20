package com.example.domainmodule.errors

sealed class CustomError(message: String) : Throwable(message) {
    class InvalidAuthToken : CustomError("Auth token value cannot be null")
}