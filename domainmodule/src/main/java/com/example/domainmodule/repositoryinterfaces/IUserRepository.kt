package com.example.domainmodule.repositoryinterfaces

import com.example.domainmodule.errors.DataError
import com.example.domainmodule.models.User
import com.example.domainmodule.utilities.FlowResult
import kotlinx.coroutines.flow.Flow

interface IUserRepository {
    fun getMe(): Flow<FlowResult<User, DataError>>
}