package br.com.mc2e.neuromind.domain.repositories

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.usecases.register.RegisterUserInput

interface UserRepository {
    suspend fun register(input: RegisterUserInput): Result<Unit>
    suspend fun saveUserName(name: String)
    suspend fun getUserName(): String?
    suspend fun saveUserEmail(email: String)
    suspend fun getUserEmail(): String?
}