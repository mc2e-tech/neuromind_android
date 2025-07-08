package br.com.mc2e.neuromind.data.datasources.remote

import br.com.mc2e.neuromind.data.datasources.remote.models.RefreshTokenRequest
import br.com.mc2e.neuromind.data.datasources.remote.models.LoginRequest
import br.com.mc2e.neuromind.data.datasources.remote.models.LoginResponse
import br.com.mc2e.neuromind.data.datasources.remote.models.RegisterRequest

interface AuthRemoteDataSource {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun refreshToken(request: RefreshTokenRequest): LoginResponse
    suspend fun register(request: RegisterRequest)
} 