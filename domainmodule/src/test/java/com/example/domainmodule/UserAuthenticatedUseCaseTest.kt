package com.example.domainmodule

import com.example.domainmodule.models.Authentication
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import com.example.domainmodule.usecases.IUserAuthenticatedUseCase
import com.example.domainmodule.usecases.UserAuthenticatedUseCase
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.first
import org.junit.Test

class UserAuthenticatedUseCaseTest: BaseTest() {
    private val authenticationRepository: IAuthenticationRepository = mockk(relaxed = true)
    private val sut: IUserAuthenticatedUseCase = UserAuthenticatedUseCase(authenticationRepository)

    @Test
    fun `execute() should return authentication state from repository`() = runTest {
        val user = createTestUser()
        val expectedAuthentication: Authentication = Authentication.Authenticated(user)
        val authenticationFlow: StateFlow<Authentication> = MutableStateFlow(expectedAuthentication)
        every { authenticationRepository.observeAuthentication() } returns authenticationFlow

        val result = sut.execute().take(1).first()
        assertEquals(expectedAuthentication, result)

        verify { authenticationRepository.observeAuthentication() }
    }
}