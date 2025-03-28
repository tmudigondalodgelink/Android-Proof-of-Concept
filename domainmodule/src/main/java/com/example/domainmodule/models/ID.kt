package com.example.domainmodule.models

import com.example.domainmodule.errors.IdentificationError

data class ID<T>(val value: String) {
    companion object {
        inline fun <reified T> create(value: String?): ID<T> {
            value?.let {
                return ID(it)
            }

            throw IdentificationError.EmptyIdentifier(T::class.simpleName ?: "Unknown")
        }
    }
}