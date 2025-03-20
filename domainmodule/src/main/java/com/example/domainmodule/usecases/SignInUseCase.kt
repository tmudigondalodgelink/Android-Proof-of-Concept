package com.example.domainmodule.usecases

import com.example.domainmodule.errors.AuthenticationError
import com.example.domainmodule.errors.CredentialsParsingError
import com.example.domainmodule.models.Authentication
import com.example.domainmodule.models.AuthenticationToken
import com.example.domainmodule.models.Email
import com.example.domainmodule.models.Password
import com.example.domainmodule.models.SignInResult
import com.example.domainmodule.models.User
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import com.example.domainmodule.repositoryinterfaces.ILocalStorageRepository
import com.example.domainmodule.utilities.FlowResult
import com.example.domainmodule.utilities.StorageKey
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.take

interface ISignInUseCase {
    fun execute(
        email: String?,
        password: String?
    ): Flow<FlowResult<User, AuthenticationError>>
}

final class SignInUseCase(
    private val authenticationRepository: IAuthenticationRepository,
    private val localStorageRepository: ILocalStorageRepository
): ISignInUseCase {
    override fun execute(
        email: String?,
        password: String?
    ): Flow<FlowResult<User, AuthenticationError>> = flow<FlowResult<User, AuthenticationError>> {

        try {
            val emailModel = Email.create(email)
            val passwordModel = Password.create(password)

            val signInResult = authenticationRepository.signIn(emailModel, passwordModel).first()

            when (signInResult) {
                is FlowResult.Success -> {
                    store(signInResult.value)
                    emit(FlowResult.Success(signInResult.value.user))
                }
                is FlowResult.Failure -> emit(FlowResult.Failure(AuthenticationError.DataError(signInResult.error)))
            }
        } catch (error: CredentialsParsingError) {
            emit(FlowResult.Failure(AuthenticationError.CredentialsParsingError(error)))
        }
    }.flowOn(Dispatchers.IO).take(1)

    private fun store(signInResult: SignInResult) {
        authenticationRepository.setAuthentication(Authentication.Authenticated(signInResult.user))
        localStorageRepository.putObject(StorageKey.AUTH_TOKEN, signInResult.authenticationToken, AuthenticationToken.serializer())
    }
}