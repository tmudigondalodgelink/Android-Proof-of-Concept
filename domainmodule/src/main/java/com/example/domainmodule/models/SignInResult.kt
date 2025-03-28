package com.example.domainmodule.models

data class SignInResult(
    val user: User,
    val authenticationToken: AuthenticationToken
)