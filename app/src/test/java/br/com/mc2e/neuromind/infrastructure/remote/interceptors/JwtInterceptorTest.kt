package br.com.mc2e.neuromind.infrastructure.remote.interceptors

import br.com.mc2e.neuromind.data.datasources.local.AuthLocalSecureDataSource
import br.com.mc2e.neuromind.infrastructure.remote.services.JwtDecoder
import io.mockk.*
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class JwtInterceptorTest {
    private lateinit var interceptor: JwtInterceptor
    private val mockStorage = mockk<AuthLocalSecureDataSource>()
    private val mockDecoder = mockk<JwtDecoder>()
    private val mockChain = mockk<Interceptor.Chain>()
    private val mockRequest = mockk<Request>()
    private val mockResponse = mockk<Response>()
    private val mockHttpUrl = mockk<HttpUrl>()
    private val mockRequestBuilder = mockk<Request.Builder>()

    @Before
    fun setUp() {
        interceptor = JwtInterceptor(mockStorage, mockDecoder)
        every { mockChain.request() } returns mockRequest
        every { mockChain.proceed(any()) } returns mockResponse
        every { mockStorage.clearTokens() } just Runs
        every { mockRequest.url } returns mockHttpUrl
        every { mockHttpUrl.encodedPath } returns "/api/some-endpoint"
        every { mockRequest.newBuilder() } returns mockRequestBuilder
        every { mockRequestBuilder.header(any(), any()) } returns mockRequestBuilder
        every { mockRequestBuilder.build() } returns mockRequest
    }

    @Test
    fun `should add Authorization header if token is valid`() {
        every { mockStorage.getAccessToken() } returns "token123"
        every { mockDecoder.isTokenExpired("token123") } returns false
        interceptor.intercept(mockChain)
        verify { mockChain.proceed(any()) }
        verify { mockRequestBuilder.header("Authorization", "Bearer token123") }
    }

    @Test
    fun `should not add header if token is expired`() {
        every { mockStorage.getAccessToken() } returns "token123"
        every { mockDecoder.isTokenExpired("token123") } returns true
        interceptor.intercept(mockChain)
        verify { mockChain.proceed(mockRequest) }
        verify { mockStorage.clearTokens() }
    }
} 