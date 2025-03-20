package com.example.domainmodule.models

sealed class Authentication {
    data object Unauthenticated : Authentication()
    data class Authenticated(val user: User) : Authentication()
}