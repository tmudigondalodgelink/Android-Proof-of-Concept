package com.example.presentationmodule

import com.example.domainmodule.models.Email
import com.example.domainmodule.models.ID
import com.example.domainmodule.models.Name
import com.example.domainmodule.models.User

fun createTestUser(
    id: ID<User> = ID.create<User>("1"),
    firstName: Name = Name.create("John"),
    lastName: Name = Name.create("Doe"),
    email: Email = Email.create("testemail@domain.com"),
    phoneNumber: String = "1234567890",
    company: String = "Test Company",
    position: String = "Test Position"
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