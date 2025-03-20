package com.example.domainmodule.usecases

import com.example.domainmodule.models.Authentication
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import com.example.domainmodule.repositoryinterfaces.ILocalStorageRepository
import com.example.domainmodule.utilities.StorageKey

interface ISignOutUseCase {
    fun execute()
}

final class SignOutUseCase(
    private val authenticationRepository: IAuthenticationRepository,
    private val localStorageRepository: ILocalStorageRepository
): ISignOutUseCase {

    override fun execute() {
        authenticationRepository.setAuthentication(Authentication.Unauthenticated)
        localStorageRepository.removeObject(StorageKey.AUTH_TOKEN)
    }
}