package br.com.mc2e.neuromind.data.datasources.remote

import br.com.mc2e.neuromind.data.datasources.remote.apis.AuthApi
import br.com.mc2e.neuromind.data.datasources.remote.models.RefreshTokenRequest
import br.com.mc2e.neuromind.data.datasources.remote.models.LoginRequest
import br.com.mc2e.neuromind.data.datasources.remote.models.LoginResponse
import br.com.mc2e.neuromind.data.datasources.remote.models.RegisterRequest
import javax.inject.Inject
import javax.inject.Singleton

class AuthRemoteDataSourceImpl @Inject constructor(
    private val authApi: AuthApi
) : AuthRemoteDataSource {
    override suspend fun login(request: LoginRequest): LoginResponse {
        val response = authApi.login(request)
        return response.body() ?: throw IllegalStateException("Response body is null")
    }

    override suspend fun refreshToken(request: RefreshTokenRequest): LoginResponse {
        val response = authApi.refreshToken(request)
        return response.body() ?: throw IllegalStateException("Response body is null")
    }

    override suspend fun register(request: RegisterRequest) {
        val response = authApi.register(request)
        return response.body() ?: throw IllegalStateException("Response body is null")
    }
} 