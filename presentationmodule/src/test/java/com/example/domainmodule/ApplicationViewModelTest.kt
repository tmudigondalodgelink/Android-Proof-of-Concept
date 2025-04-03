package com.example.domainmodule

import com.example.domainmodule.models.Authentication
import com.example.domainmodule.usecases.IUserAuthenticatedUseCase
import com.example.presentationmodule.ApplicationCoordinatorViewModel
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Test

class ApplicationCoordinatorViewModelTest: BaseTest() {
    private val authenticatedUseCase: IUserAuthenticatedUseCase = mockk()
    private val sut = ApplicationCoordinatorViewModel(authenticatedUseCase)

    @Test
    fun `initialize should update authentication state to authenticated`() = runTest {
        val user = createTestUser()
        val authState = Authentication.Authenticated(user)
        every { authenticatedUseCase.execute() } returns flowOf(authState)

        sut.initialize()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(authState, sut.authentication.value)
    }

    @Test
    fun `initialize should keep authentication state unauthenticated on failure`() = runTest {
        every { authenticatedUseCase.execute() } returns flowOf(Authentication.Unauthenticated)

        sut.initialize()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(Authentication.Unauthenticated, sut.authentication.value)
    }
}
