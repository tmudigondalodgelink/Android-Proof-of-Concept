package com.example.architecturepoc2.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.datamodule.repositories.TestRepository
import com.example.domainmodule.repositoryinterfaces.ITestRepository
import com.example.domainmodule.usecases.ITestUseCase
import com.example.domainmodule.usecases.TestUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object TestModule {

    @Provides
    fun provideTestUseCase(testRepository: ITestRepository): ITestUseCase {
        return TestUseCase(testRepository)
    }

    @Singleton
    @Provides
    fun provideSharedPreferences(application: Application): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            "LodgeLink_preferences",
            masterKeyAlias,
            application.baseContext,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}