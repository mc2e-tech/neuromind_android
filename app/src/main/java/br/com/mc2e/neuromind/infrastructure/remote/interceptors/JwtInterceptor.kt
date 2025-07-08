package br.com.mc2e.neuromind.infrastructure.remote.interceptors

import br.com.mc2e.neuromind.data.datasources.local.AuthLocalSecureDataSource
import br.com.mc2e.neuromind.infrastructure.remote.services.JwtDecoder
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtInterceptor @Inject constructor(
    private val authLocalSecureDataSource: AuthLocalSecureDataSource,
    private val jwtDecoder: JwtDecoder
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        
        // Skip authentication for login endpoint
        if (originalRequest.url.encodedPath.contains("/auth/login")) {
            return chain.proceed(originalRequest)
        }

        val accessToken = authLocalSecureDataSource.getAccessToken()
            ?: return chain.proceed(originalRequest)

        // Check if token is expired
        if (jwtDecoder.isTokenExpired(accessToken)) {
            // Token is expired, clear it and proceed without authentication
            authLocalSecureDataSource.clearTokens()
            return chain.proceed(originalRequest)
        }

        // Add authorization header and proceed
        val authenticatedRequest = originalRequest.newBuilder()
            .header("Authorization", "Bearer $accessToken")
            .build()
        return chain.proceed(authenticatedRequest)
    }
} 