package com.example.datamodule

import com.example.architecturepoc2.LoginMutation
import com.example.datamodule.client.IGraphQLClient
import com.example.datamodule.repositories.AuthenticationRepository
import com.example.domainmodule.errors.DataError
import com.example.domainmodule.models.*
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import com.example.domainmodule.utilities.FlowResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import io.mockk.coEvery
import org.junit.Before
import org.junit.Test

class AuthenticationRepositoryTest: BaseTest() {
    private val graphQLClient: IGraphQLClient = mockk()
    private val sut: IAuthenticationRepository = AuthenticationRepository(graphQLClient)

    @Test
    fun `signIn should return success when GraphQL mutation succeeds`() = runTest {
        val email = Email.create("test.user@example.com")
        val password = Password.create("password123")
        val mockMutationResult = LoginMutation.Data(
            LoginMutation.Login(
                errorMessage = "Some login error",
                message = "Some message",
                accountToken = "testToken",
                tokenExpiry = null,
                me = LoginMutation.Me(
                    userId = "123",
                    firstName = "Test",
                    lastName = "User",
                    emailAddress = "test.user@example.com",
                    phoneNumber = "1234567890",
                    company = "Tech Corp",
                    position = "Engineer",
                    activeOrganization = LoginMutation.ActiveOrganization(
                        id = 1,
                        roles = null,
                        name = "Tech Corp",
                        permissions = null
                )
            )
        ))
        coEvery { graphQLClient.mutate(any<LoginMutation>()) } returns mockMutationResult

        val result = sut.signIn(email, password).first()

        val expectedUser = createTestUser(
            id = ID.create("123"),
            firstName = Name.create("Test"),
            lastName = Name.create("User"),
            email = Email.create("test.user@example.com"),
            phoneNumber = "1234567890",
            company = "Tech Corp",
            position = "Engineer"
        )

        val expectedAuthToken = AuthenticationToken.create("testToken")
        val expectedSignInResult: FlowResult<SignInResult, DataError> = FlowResult.Success(SignInResult(expectedUser, expectedAuthToken))
        assertEquals(expectedSignInResult, result)
    }

    @Test
    fun `signIn should return failure when GraphQL mutation throws an exception`() = runTest {
        val email = Email.create("test.user@example.com")
        val password = Password.create("password123")
        val exception = RuntimeException("Network error")
        coEvery { graphQLClient.mutate(any<LoginMutation>()) } throws exception

        val result = sut.signIn(email, password).first()

        assert(result is FlowResult.Failure && result.error is DataError.ParsingError)
    }

    @Test
    fun `setAuthentication should update authentication state`() {
        val user = createTestUser()

        sut.setAuthentication(Authentication.Authenticated(user))

        assertEquals(Authentication.Authenticated(user), sut.getAuthentication())
    }

    @Test
    fun `observeAuthentication should emit updated authentication state`() = runTest {
        val user = createTestUser()

        sut.setAuthentication(Authentication.Authenticated(user))

        assertEquals(Authentication.Authenticated(user), sut.observeAuthentication().first())
    }
}