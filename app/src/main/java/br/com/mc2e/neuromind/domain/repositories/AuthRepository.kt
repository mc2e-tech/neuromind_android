package br.com.mc2e.neuromind.domain.repositories

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.entities.PreRegisterInfo
import br.com.mc2e.neuromind.domain.entities.User
import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.valueObjects.Password

interface AuthRepository {
    suspend fun login(email: Email, password: Password): Result<Unit>
    suspend fun getRefreshToken(): Result<String>
    suspend fun refreshSession(refreshToken: String): Result<Unit>
    suspend fun getPreRegisterInfo(): Result<PreRegisterInfo>
    suspend fun logout(): Result<Unit>
    suspend fun getCurrentUser(): Result<User>
} 