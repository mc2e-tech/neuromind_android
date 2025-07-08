//package br.com.mc2e.neuromind.infrastructure.remote.interceptors
//
//import br.com.mc2e.neuromind.data.datasources.local.AuthLocalSecureDataSource
//import br.com.mc2e.neuromind.data.datasources.remote.AuthRemoteDataSource
//import br.com.mc2e.neuromind.data.datasources.remote.models.RefreshTokenRequest
//import io.mockk.*
//import okhttp3.Interceptor
//import okhttp3.Request
//import okhttp3.Response
//import org.junit.Assert.*
//import org.junit.Before
//import org.junit.Test
//
//class TokenRefreshInterceptorTest {
//    private lateinit var interceptor: TokenRefreshInterceptor
//    private val mockLocal = mockk<AuthLocalSecureDataSource>()
//    private val mockRemote = mockk<AuthRemoteDataSource>()
//    private val mockChain = mockk<Interceptor.Chain>()
//    private val mockRequest = mockk<Request>()
//    private val mockResponse = mockk<Response>()
//
//    @Before
//    fun setUp() {
//        interceptor = TokenRefreshInterceptor(mockLocal, mockRemote, mockk())
//        every { mockChain.request() } returns mockRequest
//        every { mockChain.proceed(any()) } returns mockResponse
//    }
//
//    @Test
//    fun `should refresh token on 401 and retry`() {
//        // Simulate 401, refresh, and retry logic
//    }
//
//    @Test
//    fun `should propagate error if refresh fails`() {
//        // Simulate refresh failure
//    }
//}