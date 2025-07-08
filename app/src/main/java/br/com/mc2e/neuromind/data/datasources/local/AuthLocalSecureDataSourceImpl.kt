package br.com.mc2e.neuromind.data.datasources.local

import br.com.mc2e.neuromind.infrastructure.local.storage.SecureStorage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthLocalSecureDataSourceImpl @Inject constructor(
    private val secureStorage: SecureStorage
) : AuthLocalSecureDataSource {
    override fun saveAccessToken(token: String) {
        secureStorage.saveString(KEY_ACCESS_TOKEN, token)
    }

    override fun getAccessToken(): String? {
        return secureStorage.getString(KEY_ACCESS_TOKEN)
    }

    override fun saveRefreshToken(token: String) {
        secureStorage.saveString(KEY_REFRESH_TOKEN, token)
    }

    override fun getRefreshToken(): String? {
        return secureStorage.getString(KEY_REFRESH_TOKEN)
    }

    override fun clearTokens() {
        secureStorage.clear(KEY_ACCESS_TOKEN)
        secureStorage.clear(KEY_REFRESH_TOKEN)
    }

    override fun hasValidTokens(): Boolean {
        return getAccessToken() != null && getRefreshToken() != null
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
    }
} 