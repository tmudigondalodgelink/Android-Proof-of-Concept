package com.example.domainmodule.repositoryinterfaces

import com.example.domainmodule.utilities.StorageKey
import kotlinx.serialization.KSerializer

interface ILocalStorageRepository {
    fun <T> putObject(key: StorageKey, data: T, serializer: KSerializer<T>)
    fun <T> getObject(key: StorageKey, serializer: KSerializer<T>): T
    fun removeObject(key: StorageKey)
}