package com.example.datamodule.repositories

import com.example.architecturepoc2.LoginMutation
import com.example.datamodule.client.IGraphQLClient
import com.example.domainmodule.errors.DataError
import com.example.domainmodule.models.Authentication
import com.example.domainmodule.models.AuthenticationToken
import com.example.domainmodule.models.Email
import com.example.domainmodule.models.ID
import com.example.domainmodule.models.Name
import com.example.domainmodule.models.Password
import com.example.domainmodule.models.SignInResult
import com.example.domainmodule.models.User
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import com.example.domainmodule.utilities.FlowResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AuthenticationRepository @Inject constructor(
    private val graphQLClient: IGraphQLClient
) : IAuthenticationRepository{
    private var authentication: Authentication = Authentication.Unauthenticated

    override fun signIn(
        email: Email,
        password: Password
    ): Flow<FlowResult<SignInResult, DataError>> = flow<FlowResult<SignInResult, DataError>> {
        val loginMutation = LoginMutation(email.value, password.value)
        val loginResult = graphQLClient.mutate(loginMutation)
        val user = User(
            id = ID.create(loginResult?.login?.me?.userId),
            firstName = Name.create(loginResult?.login?.me?.firstName),
            lastName = Name.create(loginResult?.login?.me?.lastName),
            email = Email.create(loginResult?.login?.me?.emailAddress),
            phoneNumber = loginResult?.login?.me?.phoneNumber,
            company = loginResult?.login?.me?.company,
            position = loginResult?.login?.me?.position
        )
        val authenticationToken = AuthenticationToken.create(loginResult?.login?.accountToken)
        val signInResult = SignInResult(user, authenticationToken)

        emit(FlowResult.Success(signInResult))
    }.catch {
        when (it) {
            is DataError -> emit(FlowResult.Failure(it))
        }

        emit(FlowResult.Failure(DataError.ParsingError(it)))
    }

    @Synchronized
    override fun setAuthentication(authentication: Authentication) {
        this.authentication = authentication
    }

    @Synchronized
    override fun getAuthentication(): Authentication {
        return authentication
    }
}