package com.example.presentationmodule

import com.example.domainmodule.errors.DataError
import com.example.domainmodule.errors.ExecutionError
import com.example.domainmodule.models.User
import com.example.domainmodule.repositoryinterfaces.IUserRepository
import com.example.domainmodule.usecases.GetMeUseCase
import com.example.domainmodule.usecases.IGetMeUseCase
import com.example.domainmodule.utilities.FlowResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMeUseCaseTest: BaseTest() {
    private lateinit var getMeUseCase: IGetMeUseCase
    private lateinit var userRepository: IUserRepository

    @Before
    override fun setUp() {
        super.setUp()
        userRepository = mockk<IUserRepository>(relaxed = true)
        getMeUseCase = GetMeUseCase(userRepository)
    }

    @After
    override fun tearDown() {
        super.tearDown()
    }

    @Test
    fun `execute() should return user successfully`(): Unit = runTest {
        val user = createTestUser()
        val expectedResult: FlowResult<User, DataError> = FlowResult.Success(user)

        every { userRepository.getMe() } returns flowOf(expectedResult)

        val result = getMeUseCase.execute().first()

        assertEquals(expectedResult, result)

        verify(exactly = 1) { userRepository.getMe() }
    }

    @Test
    fun `execute() should return error`() = runTest {
        val error = DataError.NetworkError(ExecutionError.WithMessage("Network error"))
        val expectedResult: FlowResult<User, DataError> = FlowResult.Failure(error)

        every { userRepository.getMe() } returns flowOf(expectedResult)

        val result = getMeUseCase.execute().first()

        assertEquals(expectedResult, result)

        verify(exactly = 1) { userRepository.getMe() }
    }
}
