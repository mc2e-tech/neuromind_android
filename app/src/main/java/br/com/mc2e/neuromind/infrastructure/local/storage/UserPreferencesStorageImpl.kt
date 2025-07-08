package br.com.mc2e.neuromind.infrastructure.local.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class UserPreferencesStorageImpl @Inject constructor (
    @ApplicationContext private val context: Context
)  : UserPreferencesStorage {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    override suspend fun <T> save(key: String, value: T) {
        context.dataStore.edit { preferences ->
            when (value) {
                is String -> preferences[stringPreferencesKey(key)] = value
                is Boolean -> preferences[booleanPreferencesKey(key)] = value
                is Int -> preferences[intPreferencesKey(key)] = value
                is Long -> preferences[longPreferencesKey(key)] = value
                is Float -> preferences[floatPreferencesKey(key)] = value
                is Double -> preferences[doublePreferencesKey(key)] = value
                else -> throw IllegalArgumentException("Unsupported type: ${value?.javaClass?.simpleName ?: "null"}")
            }
        }
    }

    override suspend fun <T> get(key: String, defaultValue: T): T {
        val preferences = context.dataStore.data.first()
        return when (defaultValue) {
            is String -> preferences[stringPreferencesKey(key)] ?: defaultValue
            is Boolean -> preferences[booleanPreferencesKey(key)] ?: defaultValue
            is Int -> preferences[intPreferencesKey(key)] ?: defaultValue
            is Long -> preferences[longPreferencesKey(key)] ?: defaultValue
            is Float -> preferences[floatPreferencesKey(key)] ?: defaultValue
            is Double -> preferences[doublePreferencesKey(key)] ?: defaultValue
            else -> throw IllegalArgumentException("Unsupported type: ${defaultValue?.javaClass?.simpleName ?: "null"}")
        } as T
    }

    override suspend fun <T> get(key: String): T? {
        val preferences = context.dataStore.data.first()
        return when {
            preferences.contains(stringPreferencesKey(key)) -> preferences[stringPreferencesKey(key)] as T?
            preferences.contains(booleanPreferencesKey(key)) -> preferences[booleanPreferencesKey(key)] as T?
            preferences.contains(intPreferencesKey(key)) -> preferences[intPreferencesKey(key)] as T?
            preferences.contains(longPreferencesKey(key)) -> preferences[longPreferencesKey(key)] as T?
            preferences.contains(floatPreferencesKey(key)) -> preferences[floatPreferencesKey(key)] as T?
            preferences.contains(doublePreferencesKey(key)) -> preferences[doublePreferencesKey(key)] as T?
            else -> null
        }
    }

    override suspend fun remove(key: String) {
        context.dataStore.edit { preferences ->
            preferences.remove(stringPreferencesKey(key))
            preferences.remove(booleanPreferencesKey(key))
            preferences.remove(intPreferencesKey(key))
            preferences.remove(longPreferencesKey(key))
            preferences.remove(floatPreferencesKey(key))
            preferences.remove(doublePreferencesKey(key))
        }
    }

    override suspend fun clear() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}