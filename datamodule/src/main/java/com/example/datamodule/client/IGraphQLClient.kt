package com.example.datamodule.client

import com.apollographql.apollo3.api.Mutation
import com.apollographql.apollo3.api.Operation
import com.apollographql.apollo3.api.Query

interface IGraphQLClient {
    suspend fun <D : Query.Data> query(query: Query<D>): D?
    suspend fun <D : Mutation.Data> mutate(mutation: Mutation<D>): D?
}