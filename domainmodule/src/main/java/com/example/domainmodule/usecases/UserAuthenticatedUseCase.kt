package com.example.domainmodule.usecases

import com.example.domainmodule.models.Authentication
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import kotlinx.coroutines.flow.Flow


interface IUserAuthenticatedUseCase {
    fun execute() : Flow<Authentication>
}

class UserAuthenticatedUseCase(
    private val authenticationRepository: IAuthenticationRepository,
): IUserAuthenticatedUseCase {

    override fun execute(): Flow<Authentication> = authenticationRepository.observeAuthentication()
}
