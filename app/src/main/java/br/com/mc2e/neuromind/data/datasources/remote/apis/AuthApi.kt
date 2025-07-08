package br.com.mc2e.neuromind.data.datasources.remote.apis

import br.com.mc2e.neuromind.data.datasources.remote.models.LoginRequest
import br.com.mc2e.neuromind.data.datasources.remote.models.LoginResponse
import br.com.mc2e.neuromind.data.datasources.remote.models.RefreshTokenRequest
import br.com.mc2e.neuromind.data.datasources.remote.models.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApi {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("auth/refresh")
    suspend fun refreshToken(@Body request: RefreshTokenRequest): Response<LoginResponse>

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): Response<Unit>
} 