package com.example.domainmodule.errors
import kotlinx.serialization.Serializable

sealed class AccessError(message: String) : Throwable(message) {
     class NoPermission : AccessError("No permission to access the resource.")
}