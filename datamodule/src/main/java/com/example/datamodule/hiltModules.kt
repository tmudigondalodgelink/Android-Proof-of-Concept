package com.example.datamodule

import GraphQLClient
import com.example.datamodule.client.IGraphQLClient
import com.example.datamodule.repositories.AuthenticationRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
class GraphQLModule {
    @Provides
    fun providesGraphQLClient(): IGraphQLClient {
        return GraphQLClient()
    }

    @Singleton
    @Provides
    fun providesAuthenticationRepository(graphQLClient: IGraphQLClient): IAuthenticationRepository {
        return AuthenticationRepository(graphQLClient)
    }
}


