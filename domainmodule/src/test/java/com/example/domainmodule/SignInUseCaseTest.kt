package com.example.domainmodule

import com.example.domainmodule.errors.AuthenticationError
import com.example.domainmodule.errors.CredentialsParsingError
import com.example.domainmodule.errors.DataError
import com.example.domainmodule.errors.ExecutionError
import com.example.domainmodule.models.Authentication
import com.example.domainmodule.models.AuthenticationToken
import com.example.domainmodule.models.Email
import com.example.domainmodule.models.ID
import com.example.domainmodule.models.SignInResult
import com.example.domainmodule.models.User
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import com.example.domainmodule.repositoryinterfaces.ILocalStorageRepository
import com.example.domainmodule.usecases.ISignInUseCase
import com.example.domainmodule.usecases.SignInUseCase
import com.example.domainmodule.utilities.FlowResult
import com.example.domainmodule.utilities.StorageKey
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SignInUseCaseUnitTest: BaseTest() {
    private lateinit var signInUseCase: ISignInUseCase
    private val authenticationRepository: IAuthenticationRepository = mockk(relaxed = true)
    private val localStorageRepository: ILocalStorageRepository = mockk(relaxed = true)

    @Before
    override fun setUp() {
        super.setUp()
        signInUseCase = SignInUseCase(authenticationRepository, localStorageRepository)

    }

    @Test
    fun `execute() should return User successfully when credentials are valid`(): Unit = runTest {
        val email = Email.create("testUser@domain.com")
        val password = "ValidPass123"
        val user = createTestUser(email = email)
        val authToken = AuthenticationToken.create("valid-token")
        val signInResult = SignInResult(user, authToken)
        val expectedResult = FlowResult.Success<User, AuthenticationError>(user)

        every { authenticationRepository.signIn(any(), any()) } returns flowOf(FlowResult.Success(signInResult))

        val result = signInUseCase.execute(email.value, password).first()

        assertEquals(expectedResult, result)

        verify(exactly = 1) { localStorageRepository.removeObject(StorageKey.AUTH_TOKEN) }
        verify(exactly = 1) { authenticationRepository.setAuthentication(Authentication.Authenticated(user)) }
        verify(exactly = 1) { localStorageRepository.putObject(StorageKey.AUTH_TOKEN, authToken, AuthenticationToken.serializer()) }
        verify(exactly = 1) { authenticationRepository.signIn(any(), any()) }
    }

    @Test
    fun `execute() should return failure when email format is invalid`() = runTest {
        val expectedError = AuthenticationError.CredentialsParsingError(CredentialsParsingError.InvalidEmail())

        val result = signInUseCase.execute("invalid-email", "ValidPass123").first()

        assertTrue(result is FlowResult.Failure)
        val actualError = (result as FlowResult.Failure).error
        assertTrue(actualError is AuthenticationError.CredentialsParsingError)
        assertEquals((actualError as AuthenticationError.CredentialsParsingError).message, expectedError.message)
    }

    @Test
    fun `execute() should return failure when password format is invalid`() = runTest {
        val invalidPassword = ""
        val expectedError = AuthenticationError.CredentialsParsingError(CredentialsParsingError.InvalidPassword())

        val result = signInUseCase.execute("test@example.com", invalidPassword).first()

        assertTrue(result is FlowResult.Failure)
        val actualError = (result as FlowResult.Failure).error
        assertTrue(actualError is AuthenticationError.CredentialsParsingError)
        assertEquals((actualError as AuthenticationError.CredentialsParsingError).message, expectedError.message)
    }

    @Test
    fun `execute() should return failure when repository returns an error`() = runTest {
        val email = "test@example.com"
        val password = "ValidPass123"
        val expectedError = DataError.NetworkError(ExecutionError.WithMessage("Network error"))

        every { authenticationRepository.signIn(any(), any()) } returns flowOf(FlowResult.Failure(expectedError))

        val result = signInUseCase.execute(email, password).first()

        assertTrue(result is FlowResult.Failure)
        assertEquals(expectedError.message, (result as FlowResult.Failure).error.message)

        verify { localStorageRepository.removeObject(StorageKey.AUTH_TOKEN) }
        verify { authenticationRepository.signIn(any(), any()) }
    }

    @Test
    fun `execute() should take only the first result`() = runTest {
        val email = "test@example.com"
        val password = "ValidPass123"
        val user = createTestUser(email = Email.create(email))
        val authToken = AuthenticationToken.create("valid-token")
        val signInResult = SignInResult(user, authToken)

        val flow: Flow<FlowResult<SignInResult, DataError>> = flow {
            emit(FlowResult.Success(signInResult))
            emit(FlowResult.Success(SignInResult(createTestUser(id = ID.create<User>("2")), authToken)))
        }

        every { authenticationRepository.signIn(any(), any()) } returns flow

        val result = signInUseCase.execute(email, password).first()

        val expectedResult: FlowResult<User, AuthenticationError> = FlowResult.Success(user)
        assertEquals(expectedResult, result)
        verify { authenticationRepository.signIn(any(), any()) }
    }
}