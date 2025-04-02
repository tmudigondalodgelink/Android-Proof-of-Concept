package com.example.domainmodule

import com.example.domainmodule.errors.DataError
import com.example.domainmodule.errors.ExecutionError
import com.example.domainmodule.usecases.IGetMeUseCase
import com.example.domainmodule.usecases.ISignOutUseCase
import com.example.domainmodule.utilities.FlowResult
import com.example.presentationmodule.myaccount.IMyAccountViewModel
import com.example.presentationmodule.myaccount.MyAccountViewModel
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Test

class MyAccountViewModelTest: BaseTest() {
    private val getMeUseCase: IGetMeUseCase = mockk(relaxed = true)
    private val signOutUseCase: ISignOutUseCase = mockk(relaxed = true)
    private val sut: IMyAccountViewModel = MyAccountViewModel(getMeUseCase, signOutUseCase)

    @Test
    fun `increment should increase counter value`() = runTest {
        assertEquals(0, sut.counter.value)
        sut.increment()
        assertEquals(1, sut.counter.value)
    }

    @Test
    fun `getMe should update user state on success`() = runTest {
        val user = createTestUser()
        every { getMeUseCase.execute() } returns flowOf(FlowResult.Success(user))

        sut.getMe()
        //Ensure flow completes before asserting
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(user, sut.user.value)
    }

    @Test
    fun `getMe should not update user state on failure`() = runTest {
        val error = DataError.NetworkError(
            ExecutionError.WithMessage("Error fetching user")
        )
        every { getMeUseCase.execute() } returns flowOf(FlowResult.Failure(error))

        sut.getMe()
        testDispatcher.scheduler.advanceUntilIdle()

        assertEquals(null, sut.user.value)
    }

    @Test
    fun `signOut should call signOutUseCase`() = runTest {
        sut.signOut()
        verify { signOutUseCase.execute() }
    }
}