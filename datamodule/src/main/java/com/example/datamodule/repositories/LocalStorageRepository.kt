package com.example.datamodule.repositories

import android.content.SharedPreferences
import com.example.domainmodule.repositoryinterfaces.ILocalStorageRepository
import com.example.domainmodule.utilities.StorageKey
import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import java.util.Base64

class LocalStorageRepository(
    private val sharedPreferences: SharedPreferences
): ILocalStorageRepository {
    private val json = Json { ignoreUnknownKeys = true }

    override fun <T> putObject(key: StorageKey, data: T, serializer: KSerializer<T>) {
        val jsonString = json.encodeToString(serializer, data)
        val encoded = Base64.getEncoder().encodeToString(jsonString.toByteArray())
        sharedPreferences.edit().putString(key.value, encoded).apply()
    }

    override fun <T> getObject(key: StorageKey, serializer: KSerializer<T>): T {
        val encoded = sharedPreferences.getString(key.value, null)
            ?: throw IllegalArgumentException("No value found for key: ${key.value}")

        val jsonString = String(Base64.getDecoder().decode(encoded))
        return json.decodeFromString(serializer, jsonString)
    }

    override fun removeObject(key: StorageKey) {
        sharedPreferences.edit().remove(key.value).apply()
    }
}