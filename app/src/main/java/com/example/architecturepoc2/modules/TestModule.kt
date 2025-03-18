package com.example.architecturepoc2.modules

import com.example.datamodule.repositories.TestRepository
import com.example.domainmodule.repositoryinterfaces.ITestRepository
import com.example.domainmodule.usecases.ITestUseCase
import com.example.domainmodule.usecases.TestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
object TestModule {

    @Provides
    fun provideTestUseCase(testRepository: ITestRepository): ITestUseCase {
        return TestUseCase(testRepository)
    }
}