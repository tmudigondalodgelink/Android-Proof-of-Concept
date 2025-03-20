package com.example.domainmodule.repositoryinterfaces

import com.example.domainmodule.utilities.StorageKey
import kotlinx.serialization.KSerializer

interface ILocalStorageRepository {
    fun <T> putObject(key: StorageKey, data: T, serializer: KSerializer<T>)
    fun <T> getObject(key: StorageKey, serializer: KSerializer<T>): T
    fun removeObject(key: StorageKey)
}

/*
class KotlinxSerializer(
    private val sharedPreferences: SharedPreferences
) : ILocalStorageRepository {

    private val json = Json { ignoreUnknownKeys = true }

    override fun <T> putObject(key: String, data: T, serializer: KSerializer<T>) {
        val jsonString = json.encodeToString(serializer, data)
        val encoded = Base64.getEncoder().encodeToString(jsonString.toByteArray())
        sharedPreferences.edit().putString(key, encoded).apply()
    }

    override fun <T> getObject(key: String, serializer: KSerializer<T>): T {
        val encoded = sharedPreferences.getString(key, null)
            ?: throw IllegalArgumentException("No value found for key: $key")

        val jsonString = String(Base64.getDecoder().decode(encoded))
        return json.decodeFromString(serializer, jsonString)
    }
}
 */