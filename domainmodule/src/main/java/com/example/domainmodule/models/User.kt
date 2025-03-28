package com.example.domainmodule.models

data class User(
    val id: ID<User>,
    val firstName: Name,
    val lastName: Name,
    val email: Email,
    val phoneNumber: String?,
    val company: String?,
    val position: String?
)