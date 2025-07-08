package br.com.mc2e.neuromind.data.datasources.local

interface AuthLocalSecureDataSource {
    fun saveAccessToken(token: String)
    fun getAccessToken(): String?
    fun saveRefreshToken(token: String)
    fun getRefreshToken(): String?
    fun clearTokens()
    fun hasValidTokens(): Boolean
}