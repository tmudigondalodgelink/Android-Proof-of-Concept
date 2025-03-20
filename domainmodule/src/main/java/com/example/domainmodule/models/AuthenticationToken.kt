package com.example.domainmodule.models

import com.example.domainmodule.errors.AuthenticationError
import com.example.domainmodule.errors.ExecutionError
import com.example.domainmodule.errors.DataError.ParsingError
import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationToken private constructor(
    val value: String
) {
    companion object {
        fun create(value: String?): AuthenticationToken {
            value?.takeIf { it.isNotBlank() }?.let {
                return AuthenticationToken(it)
            } ?: throw AuthenticationError.DataError(ParsingError(ExecutionError.WithMessage("Auth token is empty")))

        }
    }
}