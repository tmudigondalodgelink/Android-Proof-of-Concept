package com.example.domainmodule.usecases

import com.example.domainmodule.errors.DataError
import com.example.domainmodule.models.User
import com.example.domainmodule.repositoryinterfaces.IAuthenticationRepository
import com.example.domainmodule.repositoryinterfaces.IUserRepository
import com.example.domainmodule.utilities.FlowResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.take

interface  IGetMeUseCase {
    fun execute(): Flow<FlowResult<User, DataError>>
}

final class GetMeUseCase(
    private val userRepository: IUserRepository
): IGetMeUseCase {

    override fun execute(): Flow<FlowResult<User, DataError>> = userRepository.getMe()
        .flowOn(Dispatchers.IO)
        .take(1)
}