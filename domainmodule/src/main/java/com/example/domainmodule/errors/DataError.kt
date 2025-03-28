package com.example.domainmodule.errors

sealed class DataError(private val error: Throwable) : Throwable(error.message) {
    class NetworkError(error: Throwable) : DataError(error)
    class ResponseError(error: Throwable) : DataError(error)
    class ParsingError(error: Throwable) : DataError(error)
    class PersistenceError(error: Throwable) : DataError(error)
    class OtherError(error: Throwable) : DataError(error)

    val errorDescription: String?
        get() = error.localizedMessage
}