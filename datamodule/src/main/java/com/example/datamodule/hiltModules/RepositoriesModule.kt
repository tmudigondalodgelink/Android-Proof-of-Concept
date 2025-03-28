package com.example.datamodule.hiltModules

import GraphQLClient
import android.content.SharedPreferences
import com.example.datamodule.client.IGraphQLClient
import com.example.datamodule.repositories.AuthenticationRepository
import com.example.datamodule.repositories.LocalStorageRepository
import com.example.datamodule.repositories.UserRepository
import com.example.domainmodule.models.AuthenticationToken
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import com.example.domainmodule.repositoryinterfaces.ILocalStorageRepository
import com.example.domainmodule.repositoryinterfaces.IUserRepository
import com.example.domainmodule.utilities.StorageKey
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class RepositoriesModule {
    @Provides
    fun providesGraphQLClient(localStorage: ILocalStorageRepository): IGraphQLClient {
        return GraphQLClient(
            getAuthenticationToken = {
            runCatching { localStorage.getObject(StorageKey.AUTH_TOKEN, AuthenticationToken.serializer()) }.getOrNull()
        })
    }

    @Singleton
    @Provides
    fun providesAuthenticationRepository(graphQLClient: IGraphQLClient): IAuthenticationRepository {
        return AuthenticationRepository(graphQLClient)
    }

    @Provides
    fun providesUserRepository(graphQLClient: IGraphQLClient): IUserRepository {
        return UserRepository(graphQLClient)
    }

    @Provides
    fun providesLocalStorageRepository(sharedPreferences: SharedPreferences): ILocalStorageRepository {
        return LocalStorageRepository(sharedPreferences)
    }
}


