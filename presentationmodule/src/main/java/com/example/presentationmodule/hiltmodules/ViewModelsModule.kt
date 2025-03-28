package com.example.presentationmodule.hiltmodules

import com.example.domainmodule.usecases.ISignInUseCase
import com.example.presentationmodule.signin.ISignInViewModel
import com.example.presentationmodule.signin.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class ViewModelsModule {
    @Binds
    abstract fun bindsSignInViewModel(signInViewModel: SignInViewModel): ISignInViewModel
}