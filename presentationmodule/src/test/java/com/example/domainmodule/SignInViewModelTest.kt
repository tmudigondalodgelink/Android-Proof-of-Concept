package com.example.domainmodule

import com.example.domainmodule.errors.AuthenticationError
import com.example.domainmodule.errors.DataError
import com.example.domainmodule.usecases.ISignInUseCase
import com.example.domainmodule.utilities.FlowResult
import com.example.presentationmodule.signin.ISignInViewModel
import com.example.presentationmodule.signin.SignInViewModel
import io.mockk.mockk
import org.junit.Test
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals

class SignInViewModelTest: BaseTest() {
    private val signInUseCase: ISignInUseCase = mockk(relaxed = true)
    private val sut: ISignInViewModel = SignInViewModel(signInUseCase)

    @Test
    fun `setEmail should update email state`() = runTest {
        val email = "test@example.com"

        sut.setEmail(email)

        assertEquals(email, sut.email.value)
    }

    @Test
    fun `setPassword should update password state`() = runTest {
        val password = "password123"

        sut.setPassword(password)

        assertEquals(password, sut.password.value)
    }

    @Test
    fun `signIn should update onSignInSuccess on success`() = runTest {
        val user = createTestUser()
        every { signInUseCase.execute(any(), any()) } returns flowOf(FlowResult.Success(user))

        sut.signIn()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(true, sut.onSignInSuccess.value)
    }

    @Test
    fun `signIn should not update onSignInSuccess on failure`() = runTest {
        val error = AuthenticationError.DataError(
            DataError.NetworkError(Throwable("Fake data error for testing"))
        )
        every { signInUseCase.execute(any(), any()) } returns flowOf(FlowResult.Failure(error))

        sut.signIn()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(false, sut.onSignInSuccess.value)
    }

    @Test
    fun `resetState should reset email, password, and onSignInSuccess`() = runTest {
        sut.setEmail("test@example.com")
        sut.setPassword("password123")
        sut.signIn()
        testDispatcher.scheduler.advanceUntilIdle()

        sut.resetState()

        assertEquals("", sut.email.value)
        assertEquals("", sut.password.value)
        assertEquals(false, sut.onSignInSuccess.value)
    }
}