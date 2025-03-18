package com.example.architecturepoc2.modules

import com.example.datamodule.repositories.TestRepository
import com.example.domainmodule.repositoryinterfaces.ITestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object RepositoriesModule {

    @Provides
    fun provideTestRepository(): ITestRepository {
        return TestRepository()
    }
}