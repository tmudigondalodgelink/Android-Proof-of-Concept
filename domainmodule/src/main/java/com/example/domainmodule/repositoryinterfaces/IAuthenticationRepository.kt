package com.example.domainmodule.repositoryinterfaces

import android.content.SharedPreferences
import com.example.domainmodule.errors.DataError
import com.example.domainmodule.models.Authentication
import com.example.domainmodule.models.Email
import com.example.domainmodule.models.Password
import com.example.domainmodule.models.SignInResult
import com.example.domainmodule.utilities.FlowResult
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.util.Base64

interface IAuthenticationRepository {
    fun signIn(email: Email,password: Password): Flow<FlowResult<SignInResult, DataError>>
    fun setAuthentication(authentication: Authentication)
}