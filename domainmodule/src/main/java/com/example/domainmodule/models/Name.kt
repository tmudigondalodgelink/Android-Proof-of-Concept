package com.example.domainmodule.models

data class Name private constructor(
    val value: String
) {
    companion object {
        fun create(value: String?): Name {
            return Name(value ?: "")
        }
    }
}