package com.example.domainmodule.errors

sealed class CustomError(message: String) : Throwable(message) {

    class InvalidId : CustomError("ID value cannot be null")
    class InvalidAuthToken : CustomError("Auth token value cannot be null")
}