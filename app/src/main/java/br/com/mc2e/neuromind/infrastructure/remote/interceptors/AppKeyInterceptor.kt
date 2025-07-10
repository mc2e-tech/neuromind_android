package br.com.mc2e.neuromind.infrastructure.remote.interceptors

import br.com.mc2e.neuromind.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Interceptor that adds the API key header to all HTTP requests.
 * 
 * Uses BuildConfig.APP_KEY which should be defined in build.gradle.kts
 * for different build types (debug/release).
 */
@Singleton
class AppKeyInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithKey = chain.request()
            .newBuilder()
            .addHeader("x-api-key", BuildConfig.APP_KEY)
            .build()
        return chain.proceed(requestWithKey)
    }
} 