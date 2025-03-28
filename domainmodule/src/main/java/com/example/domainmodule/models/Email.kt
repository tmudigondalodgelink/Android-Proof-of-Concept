package com.example.domainmodule.models

import com.example.domainmodule.errors.CredentialsParsingError


data class Email private constructor(
    val value: String
) {
    companion object {
        fun create(value: String?): Email {
            require(!value.isNullOrBlank()) { throw CredentialsParsingError.InvalidEmail() }

            val regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$".toRegex()
            if (!regex.matches(value)) {
                throw CredentialsParsingError.InvalidEmail()
            }

            return Email(value)
        }
    }
}