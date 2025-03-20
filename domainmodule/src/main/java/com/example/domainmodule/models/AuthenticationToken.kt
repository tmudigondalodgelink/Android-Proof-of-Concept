package com.example.domainmodule.models

import com.example.domainmodule.errors.CustomError

data class AuthenticationToken private constructor(
    val value: String
) {
    companion object {
        fun create(value: String?): AuthenticationToken {
            value?.takeIf { it.isNotBlank() }?.let {
                return AuthenticationToken(it)
            } ?: throw CustomError.InvalidAuthToken()
        }
    }
}