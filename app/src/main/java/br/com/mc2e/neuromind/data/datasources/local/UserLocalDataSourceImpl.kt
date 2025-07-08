package br.com.mc2e.neuromind.data.datasources.local

import br.com.mc2e.neuromind.infrastructure.local.storage.UserPreferencesStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSourceImpl @Inject constructor(
    private val userPreferencesStorage: UserPreferencesStorage
) : UserLocalDataSource {

    companion object {
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_IS_FIRST_ACCESS = "is_first_access"
    }

    override suspend fun saveUserName(name: String) {
        userPreferencesStorage.save(KEY_USER_NAME, name)
    }

    override suspend fun getUserName(): String? {
        return userPreferencesStorage.get<String>(KEY_USER_NAME)
    }

    override suspend fun saveUserEmail(email: String) {
        userPreferencesStorage.save(KEY_USER_EMAIL, email)
    }

    override suspend fun getUserEmail(): String? {
        return userPreferencesStorage.get<String>(KEY_USER_EMAIL)
    }

    override suspend fun saveIsFirstAccess(isFirstAccess: Boolean) {
        userPreferencesStorage.save(KEY_IS_FIRST_ACCESS, isFirstAccess)
    }

    override suspend fun getIsFirstAccess(): Boolean {
        return userPreferencesStorage.get(KEY_IS_FIRST_ACCESS, true)
    }
}
