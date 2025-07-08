package br.com.mc2e.neuromind.infrastructure.local.storage

interface SecureStorage {
    fun saveString(key: String, value: String)
    fun getString(key: String): String?
    fun clear(key: String)
    fun clearAll()
} 