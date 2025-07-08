package br.com.mc2e.neuromind.data.datasources.remote

import br.com.mc2e.neuromind.data.datasources.remote.apis.AuthApi
import br.com.mc2e.neuromind.data.datasources.remote.models.LoginRequest
import br.com.mc2e.neuromind.data.datasources.remote.models.LoginResponse
import br.com.mc2e.neuromind.data.datasources.remote.models.RefreshTokenRequest
import br.com.mc2e.neuromind.data.datasources.remote.models.RegisterRequest
import io.mockk.*
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class AuthRemoteDataSourceImplTest {
    private lateinit var dataSource: AuthRemoteDataSource
    private val mockApi = mockk<AuthApi>()

    @Before
    fun setUp() {
        dataSource = AuthRemoteDataSourceImpl(mockApi)
    }

    @Test
    fun `should return user on successful login`() {
        runBlocking {
            val request = LoginRequest("email@test.com", "password")
            val response = LoginResponse("token", "refresh")
            coEvery { mockApi.login(request) } returns Response.success(response)
            val result = dataSource.login(request)
            assertEquals(response, result)
        }
    }

    @Test
    fun `should throw exception on API error`() {
        runBlocking {
            val request = LoginRequest("email@test.com", "password")
            coEvery { mockApi.login(request) } returns Response.error(
                400,
                "error".toResponseBody(null)
            )
            try {
                dataSource.login(request)
                fail("Should throw exception")
            } catch (e: Exception) {
                // The implementation throws IllegalStateException when response body is null
                assertTrue(e is IllegalStateException)
            }
        }
    }

    @Test
    fun `should return user on successful refreshToken`() = runBlocking {
        val request = RefreshTokenRequest("refresh")
        val response = LoginResponse("token", "refresh")

        coEvery { mockApi.refreshToken(request) } returns Response.success(response)

        val result = dataSource.refreshToken(request)

        assertEquals(response, result)
    }

    @Test
    fun `should throw exception on API error in refreshToken`() {
        runBlocking {
            val request =
                br.com.mc2e.neuromind.data.datasources.remote.models.RefreshTokenRequest("refresh")
            coEvery { mockApi.refreshToken(request) } returns Response.error(
                400,
                "error".toResponseBody(null)
            )
            try {
                dataSource.refreshToken(request)
                fail("Should throw exception")
            } catch (e: Exception) {
                assertTrue(e is IllegalStateException)
            }
        }
    }

    @Test
    fun `should throw exception if API throws in login`() {
        runBlocking {
            val request = LoginRequest("email@test.com", "password")
            coEvery { mockApi.login(request) } throws RuntimeException("API failure")
            try {
                dataSource.login(request)
                fail("Should throw exception")
            } catch (e: Exception) {
                assertTrue(e is RuntimeException)
                assertEquals("API failure", e.message)
            }
        }
    }

    @Test
    fun `should throw exception if API throws in refreshToken`() {
        runBlocking {
            val request =
                br.com.mc2e.neuromind.data.datasources.remote.models.RefreshTokenRequest("refresh")
            coEvery { mockApi.refreshToken(request) } throws RuntimeException("API failure")
            try {
                dataSource.refreshToken(request)
                fail("Should throw exception")
            } catch (e: Exception) {
                assertTrue(e is RuntimeException)
                assertEquals("API failure", e.message)
            }
        }
    }

    @Test
    fun `should succeed on successful register`() = runBlocking {
        val request = RegisterRequest("name", "email@test.com", "password")
        coEvery { mockApi.register(request) } returns Response.success(Unit)

        val result = dataSource.register(request)

        // No exception means success
        assertEquals(Unit, result)
    }
    @Test
    fun `should throw exception when register response body is null`() = runBlocking {
        val request = RegisterRequest("name", "email@test.com", "password")
        coEvery { mockApi.register(request) } returns Response.error(
            400,
            "error".toResponseBody(null)
        )

        try {
            dataSource.register(request)
            fail("Should throw exception")
        } catch (e: Exception) {
            assertTrue(e is IllegalStateException)
            assertEquals("Response body is null", e.message)
        }
    }

    @Test
    fun `should throw exception if API throws in register`() = runBlocking {
        val request = RegisterRequest("name", "email@test.com", "password")
        coEvery { mockApi.register(request) } throws RuntimeException("API failure")

        try {
            dataSource.register(request)
            fail("Should throw exception")
        } catch (e: Exception) {
            assertTrue(e is RuntimeException)
            assertEquals("API failure", e.message)
        }
    }

} 