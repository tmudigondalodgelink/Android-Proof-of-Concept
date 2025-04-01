package com.example.domainmodule

import com.example.domainmodule.models.Authentication
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import com.example.domainmodule.repositoryinterfaces.ILocalStorageRepository
import com.example.domainmodule.usecases.ISignOutUseCase
import com.example.domainmodule.usecases.SignOutUseCase
import com.example.domainmodule.utilities.StorageKey
import io.mockk.mockk
import io.mockk.verify
import org.junit.After
import org.junit.Before
import org.junit.Test

class SignOutUseCaseTest: BaseTest() {
    private lateinit var authenticationRepository: IAuthenticationRepository
    private lateinit var localStorageRepository: ILocalStorageRepository
    private lateinit var signOutUseCase: ISignOutUseCase

    @Before
    override fun setUp() {
        super.setUp()
        authenticationRepository = mockk(relaxed = true)
        localStorageRepository = mockk(relaxed = true)
        signOutUseCase = SignOutUseCase(authenticationRepository, localStorageRepository)
    }

    @Test
    fun `execute() should set authentication to Unauthenticated`() {
        signOutUseCase.execute()

        verify { authenticationRepository.setAuthentication(Authentication.Unauthenticated) }
    }

    @Test
    fun `execute() should remove auth token from local storage`() {
        signOutUseCase.execute()

        verify { localStorageRepository.removeObject(StorageKey.AUTH_TOKEN) }
    }
}