package br.com.mc2e.neuromind.infrastructure.local.storage

import kotlinx.coroutines.flow.Flow

/**
 * Generic interface for user preferences storage using DataStore.
 *
 * This interface defines the contract for persisting and retrieving user preference data
 * in a safe and efficient manner using generic types.
 */
interface UserPreferencesStorage {

    /**
     * Saves a value in preferences with the specified key.
     * @param key The key to identify the stored value
     * @param value The value to be saved
     */
    suspend fun <T> save(key: String, value: T)

    /**
     * Retrieves a value from preferences with the specified key.
     * @param key The key to identify the stored value
     * @param defaultValue The default value to return if the key doesn't exist
     * @return The stored value or the default value if it doesn't exist
     */
    suspend fun <T> get(key: String, defaultValue: T): T

    /**
     * Retrieves a value from preferences with the specified key.
     * @param key The key to identify the stored value
     * @return The stored value or null if it doesn't exist
     */
    suspend fun <T> get(key: String): T?

    /**
     * Removes a value from preferences with the specified key.
     * @param key The key to identify the value to be removed
     */
    suspend fun remove(key: String)

    /**
     * Clears all stored preferences.
     */
    suspend fun clear()
}