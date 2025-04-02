package com.example.datamodule
import com.example.architecturepoc2.MeQuery
import com.example.architecturepoc2.MeQuery.FeatureFlagMapping
import com.example.datamodule.client.IGraphQLClient
import com.example.datamodule.repositories.UserRepository
import com.example.domainmodule.errors.DataError
import com.example.domainmodule.errors.ExecutionError
import com.example.domainmodule.models.Email
import com.example.domainmodule.models.ID
import com.example.domainmodule.models.Name
import com.example.domainmodule.models.User
import com.example.domainmodule.repositoryinterfaces.IUserRepository
import com.example.domainmodule.utilities.FlowResult
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Assert.assertEquals
import org.junit.Test

class UserRepositoryTest: BaseTest() {
    private val graphQLClient: IGraphQLClient = mockk()
    private val sut: IUserRepository = UserRepository(graphQLClient)

    @Test
    fun `getMe() should return user successfully when GraphQL query succeeds`() = runTest {
        val mockQueryResult = MeQuery.Data(MeQuery.Me(
            userId = "123",
            firstName = "Test",
            lastName = "User",
            emailAddress = "test.user@example.com",
            position = "Engineer",
            company = "Tech Corp",
            phoneNumber = "1234567890",
            activeOrganization = MeQuery.ActiveOrganization(
                id = 1,
                roles = null,
                name = "Tech Corp",
                permissions = null,
                featureFlagMapping = null
            )
        ))
        coEvery { graphQLClient.query(any<MeQuery>()) } returns mockQueryResult

        val result = sut.getMe().first()

        val expectedUser = User(
            id = ID.create("123"),
            firstName = Name.create("Test"),
            lastName = Name.create("User"),
            email = Email.create("test.user@example.com"),
            position = "Engineer",
            company = "Tech Corp",
            phoneNumber = "1234567890"
        )
        val expectedResult: FlowResult<User, DataError> = FlowResult.Success(expectedUser)

        assertEquals(expectedResult, result)
    }

    @Test
    fun `getMe() should return DataError_ParsingError when exception occurs`() = runTest {
        val exception = RuntimeException("GraphQL query failed")
        coEvery { graphQLClient.query(any<MeQuery>()) } throws exception

        val result = sut.getMe().first()

        assert(result is FlowResult.Failure && result.error is DataError.ParsingError)
        assertEquals(exception.message, (result as FlowResult.Failure).error.message)
    }

    @Test
    fun `getMe() should return DataError_NetworkError when network error happens`() = runTest {
        val error = DataError.NetworkError(ExecutionError.WithMessage("Network error"))
        coEvery { graphQLClient.query(any<MeQuery>()) } throws error

        val result = sut.getMe().first()

        assert(result is FlowResult.Failure && result.error is DataError.NetworkError)
    }
}