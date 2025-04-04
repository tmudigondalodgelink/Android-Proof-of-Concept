package com.example.domainmodule.models

import com.example.domainmodule.errors.CredentialsParsingError
import com.example.domainmodule.errors.CustomError

data class Password private constructor(
    val value: String
) {
    companion object {
        fun create(value: String?): Password {
            require(!value.isNullOrBlank()) { throw CredentialsParsingError.InvalidPassword() }
            return Password(value)
        }
    }
}