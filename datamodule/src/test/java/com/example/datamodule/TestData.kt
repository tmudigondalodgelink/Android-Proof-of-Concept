package com.example.datamodule

import com.example.domainmodule.models.Email
import com.example.domainmodule.models.ID
import com.example.domainmodule.models.Name
import com.example.domainmodule.models.User

fun createTestUser(
    id: ID<User> = ID.create("test-id"),
    firstName: Name = Name.create("Test"),
    lastName: Name = Name.create("User"),
    email: Email = Email.create("testuser@domain.com"),
    phoneNumber: String? = "1234567890",
    company: String? = "Tech Corp",
    position: String? = "Engineer"
): User {
    return User(
        id = id,
        firstName = firstName,
        lastName = lastName,
        email = email,
        phoneNumber = phoneNumber,
        company = company,
        position = position
    )
}