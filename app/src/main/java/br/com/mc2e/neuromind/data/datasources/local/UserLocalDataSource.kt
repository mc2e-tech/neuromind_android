package br.com.mc2e.neuromind.data.datasources.local

interface UserLocalDataSource {
    suspend fun saveUserName(name: String)
    suspend fun getUserName(): String?
    suspend fun saveUserEmail(email: String)
    suspend fun getUserEmail(): String?
    suspend fun saveIsFirstAccess(isFirstAccess: Boolean)
    suspend fun getIsFirstAccess(): Boolean
}