package com.example.architecturepoc2.modules

import com.example.datamodule.repositories.AuthenticationRepository
import com.example.datamodule.repositories.LocalStorageRepository
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import com.example.domainmodule.repositoryinterfaces.ILocalStorageRepository
import com.example.domainmodule.repositoryinterfaces.IUserRepository
import com.example.domainmodule.usecases.GetMeUseCase
import com.example.domainmodule.usecases.IGetMeUseCase
import com.example.domainmodule.usecases.ISignInUseCase
import com.example.domainmodule.usecases.ISignOutUseCase
import com.example.domainmodule.usecases.SignInUseCase
import com.example.domainmodule.usecases.SignOutUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object UseCasesModule {

    @Provides
    fun provideGetMeUseCase(
        userRepository: IUserRepository,
    ): IGetMeUseCase {

        return GetMeUseCase(userRepository)
    }

    @Provides
    fun provideSignInUseCase(
        authenticationRepository: IAuthenticationRepository,
        localStorageRepository: ILocalStorageRepository
    ): ISignInUseCase {

        return SignInUseCase(authenticationRepository, localStorageRepository)
    }

    @Provides
    fun provideSignOutUseCase(
        authenticationRepository: IAuthenticationRepository,
        localStorageRepository: ILocalStorageRepository
    ): ISignOutUseCase {

        return SignOutUseCase(authenticationRepository, localStorageRepository)
    }
}