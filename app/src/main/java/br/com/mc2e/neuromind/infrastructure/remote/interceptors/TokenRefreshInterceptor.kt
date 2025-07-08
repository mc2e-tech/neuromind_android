package br.com.mc2e.neuromind.infrastructure.remote.interceptors

import br.com.mc2e.neuromind.data.datasources.local.AuthLocalSecureDataSource
import br.com.mc2e.neuromind.data.datasources.remote.AuthRemoteDataSource
import br.com.mc2e.neuromind.data.datasources.remote.models.LoginResponse
import br.com.mc2e.neuromind.data.datasources.remote.models.RefreshTokenRequest
import br.com.mc2e.neuromind.infrastructure.remote.services.JwtDecoder
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton
import dagger.Lazy

@Singleton
class TokenRefreshInterceptor @Inject constructor(
    private val authLocalSecureDataSource: AuthLocalSecureDataSource,
    private val authRemoteDataSource: Lazy<AuthRemoteDataSource>,
    private val jwtDecoder: JwtDecoder
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalResponse = chain.proceed(originalRequest)

        // If not 401 or no valid tokens, return original response
        if (originalResponse.code != 401 || !authLocalSecureDataSource.hasValidTokens()) {
            return originalResponse
        }

        originalResponse.close()
        
        return try {
            val refreshToken = authLocalSecureDataSource.getRefreshToken()
            
            // No refresh token, clear tokens and return 401
            if (refreshToken == null) {
                authLocalSecureDataSource.clearTokens()
                return originalResponse
            }

            // Check if refresh token is expired
            if (jwtDecoder.isTokenExpired(refreshToken)) {
                authLocalSecureDataSource.clearTokens()
                return originalResponse
            }

            val refreshRequest = RefreshTokenRequest(refreshToken)
            val refreshResponse: LoginResponse = runBlocking(Dispatchers.IO) {
                authRemoteDataSource.get().refreshToken(refreshRequest)
            }
            
            // Save new tokens
            authLocalSecureDataSource.saveAccessToken(refreshResponse.accessToken)
            authLocalSecureDataSource.saveRefreshToken(refreshResponse.refreshToken)
            
            // Retry the original request with new token
            val newToken = authLocalSecureDataSource.getAccessToken()
            val retryRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $newToken")
                .build()
            chain.proceed(retryRequest)
        } catch (e: Exception) {
            // Refresh failed, clear tokens and return 401
            authLocalSecureDataSource.clearTokens()
            originalResponse
        }
    }
} 