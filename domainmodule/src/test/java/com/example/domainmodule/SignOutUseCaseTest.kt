package com.example.domainmodule

import com.example.domainmodule.models.Authentication
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import com.example.domainmodule.repositoryinterfaces.ILocalStorageRepository
import com.example.domainmodule.usecases.ISignOutUseCase
import com.example.domainmodule.usecases.SignOutUseCase
import com.example.domainmodule.utilities.StorageKey
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class SignOutUseCaseTest: BaseTest() {
    private val authenticationRepository: IAuthenticationRepository = mockk(relaxed = true)
    private val localStorageRepository: ILocalStorageRepository = mockk(relaxed = true)
    private val sut: ISignOutUseCase = SignOutUseCase(authenticationRepository, localStorageRepository)

    @Test
    fun `execute() should set authentication to Unauthenticated`() {
        sut.execute()

        verify { authenticationRepository.setAuthentication(Authentication.Unauthenticated) }
    }

    @Test
    fun `execute() should remove auth token from local storage`() {
        sut.execute()

        verify { localStorageRepository.removeObject(StorageKey.AUTH_TOKEN) }
    }
}