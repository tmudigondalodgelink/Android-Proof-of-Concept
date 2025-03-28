package com.example.datamodule.repositories

import com.example.architecturepoc2.MeQuery
import com.example.datamodule.client.IGraphQLClient
import com.example.domainmodule.errors.DataError
import com.example.domainmodule.models.Email
import com.example.domainmodule.models.ID
import com.example.domainmodule.models.Name
import com.example.domainmodule.models.User
import com.example.domainmodule.repositoryinterfaces.IUserRepository
import com.example.domainmodule.utilities.FlowResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val graphQLClient: IGraphQLClient
): IUserRepository {
    override fun getMe(): Flow<FlowResult<User, DataError>> = flow {
        try {
            val queryMe = graphQLClient.query(MeQuery())
            val user = User(
                id = ID.create(queryMe?.me?.userId),
                firstName = Name.create(queryMe?.me?.firstName),
                lastName = Name.create(queryMe?.me?.lastName),
                email = Email.create(queryMe?.me?.emailAddress),
                position = queryMe?.me?.position,
                company = queryMe?.me?.company,
                phoneNumber = queryMe?.me?.phoneNumber
            )
            emit(FlowResult.Success(user))
        } catch (e: Exception) {
            emit(FlowResult.Failure(DataError.ParsingError(e)))
        } catch (dataError: DataError) {
            emit(FlowResult.Failure(dataError))
        }
    }
}