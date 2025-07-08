package br.com.mc2e.neuromind.data.repositories

import br.com.mc2e.neuromind.data.datasources.local.AuthLocalSecureDataSource
import br.com.mc2e.neuromind.data.datasources.local.UserLocalDataSource
import br.com.mc2e.neuromind.data.datasources.remote.AuthRemoteDataSource
import br.com.mc2e.neuromind.data.datasources.remote.models.LoginRequest
import br.com.mc2e.neuromind.data.datasources.remote.models.LoginResponse
import br.com.mc2e.neuromind.data.datasources.remote.models.RefreshTokenRequest
import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.entities.PreRegisterInfo
import br.com.mc2e.neuromind.domain.entities.User
import br.com.mc2e.neuromind.domain.failures.AuthFailure
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.valueObjects.Name
import br.com.mc2e.neuromind.domain.valueObjects.Password
import br.com.mc2e.neuromind.infrastructure.remote.models.JwtPayload
import br.com.mc2e.neuromind.infrastructure.remote.services.JwtDecoder
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.HttpException
import java.io.IOException
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

class AuthRepositoryImplTest {
    private lateinit var repository: AuthRepositoryImpl
    private val mockRemote = mockk<AuthRemoteDataSource>()
    private val mockLocal = mockk<AuthLocalSecureDataSource>()
    private val mockUserLocal = mockk<UserLocalDataSource>()
    private val mockJwtDecoder = mockk<JwtDecoder>()

    @Before
    fun setUp() {
        repository = AuthRepositoryImpl(mockRemote, mockLocal, mockUserLocal, mockJwtDecoder)
    }

    @Test
    fun `should return success on valid login`() = runBlocking {
        // Arrange
        val email = Email.create("test@example.com")
        val password = Password.create("Password123!")
        val loginRequest = LoginRequest(email = email.getValue(), password = password.getValue())
        val loginResponse = LoginResponse(accessToken = "access_token", refreshToken = "refresh_token")
        
        coEvery { mockRemote.login(loginRequest) } returns loginResponse
        coEvery { mockLocal.saveAccessToken("access_token") } just Runs
        coEvery { mockLocal.saveRefreshToken("refresh_token") } just Runs

        // Act
        val result = repository.login(email, password)

        // Assert
        assertTrue(result is Result.Success)
        coVerify { 
            mockRemote.login(loginRequest)
            mockLocal.saveAccessToken("access_token")
            mockLocal.saveRefreshToken("refresh_token")
        }
    }

    @Test
    fun `should return invalid credentials error on 401 response for login`() = runBlocking {
        // Arrange
        val email = Email.create("test@example.com")
        val password = Password.create("WrongPass123!")
        val loginRequest = LoginRequest(email = email.getValue(), password = password.getValue())
        val httpException = HttpException(Response.error<Any>(401, "".toResponseBody()))
        
        coEvery { mockRemote.login(loginRequest) } throws httpException

        // Act
        val result = repository.login(email, password)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(AuthFailure.InvalidCredentials, (result as Result.Error).failure)
        coVerify { mockRemote.login(loginRequest) }
        coVerify(exactly = 0) { 
            mockLocal.saveAccessToken(any())
            mockLocal.saveRefreshToken(any())
        }
    }

    @Test
    fun `should return server error on other http errors for login`() = runBlocking {
        // Arrange
        val email = Email.create("test@example.com")
        val password = Password.create("Password123!")
        val loginRequest = LoginRequest(email = email.getValue(), password = password.getValue())
        val httpException = HttpException(Response.error<Any>(500, "".toResponseBody()))
        
        coEvery { mockRemote.login(loginRequest) } throws httpException

        // Act
        val result = repository.login(email, password)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(Failure.ServerError, (result as Result.Error).failure)
        coVerify { mockRemote.login(loginRequest) }
        coVerify(exactly = 0) { 
            mockLocal.saveAccessToken(any())
            mockLocal.saveRefreshToken(any())
        }
    }

    @Test
    fun `should return local error on IOException for login`() = runBlocking {
        // Arrange
        val email = Email.create("test@example.com")
        val password = Password.create("Password123!")
        val loginRequest = LoginRequest(email = email.getValue(), password = password.getValue())
        
        coEvery { mockRemote.login(loginRequest) } throws IOException("Error")

        // Act
        val result = repository.login(email, password)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(Failure.LocalError, (result as Result.Error).failure)
        coVerify { mockRemote.login(loginRequest) }
        coVerify(exactly = 0) { 
            mockLocal.saveAccessToken(any())
            mockLocal.saveRefreshToken(any())
        }
    }

    @Test
    fun `should return unexpected error on other exceptions for login`() = runBlocking {
        // Arrange
        val email = Email.create("test@example.com")
        val password = Password.create("Password123!")
        val loginRequest = LoginRequest(email = email.getValue(), password = password.getValue())
        val exception = RuntimeException("Unexpected error")
        
        coEvery { mockRemote.login(loginRequest) } throws exception

        // Act
        val result = repository.login(email, password)

        // Assert
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.failure is Failure.Unexpected)
        assertEquals(exception, (error.failure as Failure.Unexpected).exception)
        coVerify { mockRemote.login(loginRequest) }
        coVerify(exactly = 0) { 
            mockLocal.saveAccessToken(any())
            mockLocal.saveRefreshToken(any())
        }
    }

    @Test
    fun `should clear tokens on logout success`() = runBlocking {
        // Arrange
        coEvery { mockLocal.clearTokens() } just Runs

        // Act
        val result = repository.logout()

        // Assert
        assertTrue(result is Result.Success)
        coVerify { mockLocal.clearTokens() }
    }

    @Test
    fun `should return error when logout fails`() = runBlocking {
        // Arrange
        val exception = RuntimeException("Storage error")
        coEvery { mockLocal.clearTokens() } throws exception

        // Act
        val result = repository.logout()

        // Assert
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.failure is Failure.Unexpected)
        assertEquals(exception, (error.failure as Failure.Unexpected).exception)
        coVerify { mockLocal.clearTokens() }
    }

    @Test
    fun `should return current user when token is valid`() = runBlocking {
        // Arrange
        val token = "valid_token"
        val jwtPayload = JwtPayload(
            userId = "user123",
            email = "test@example.com",
            name = "Test User",
            role = "user",
            issuedAt = 1234567890L,
            expiresAt = 1234567890L + 3600L
        )
        val expectedUser = User(
            id = "user123",
            name = Name.create("Test User"),
            email = Email.create("test@example.com")
        )
        
        coEvery { mockLocal.getAccessToken() } returns token
        every { mockJwtDecoder.decodeToken(token) } returns jwtPayload

        // Act
        val result = repository.getCurrentUser()

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(expectedUser, (result as Result.Success).data)
        coVerify { mockLocal.getAccessToken() }
        verify { mockJwtDecoder.decodeToken(token) }
    }

    @Test
    fun `should return unauthorized when no token exists`() = runBlocking {
        // Arrange
        coEvery { mockLocal.getAccessToken() } returns null

        // Act
        val result = repository.getCurrentUser()

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(Failure.Unauthorized, (result as Result.Error).failure)
        coVerify { mockLocal.getAccessToken() }
        verify(exactly = 0) { mockJwtDecoder.decodeToken(any()) }
    }

    @Test
    fun `should return unauthorized when token is invalid`() = runBlocking {
        // Arrange
        val token = "invalid_token"
        
        coEvery { mockLocal.getAccessToken() } returns token
        every { mockJwtDecoder.decodeToken(token) } returns null

        // Act
        val result = repository.getCurrentUser()

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(Failure.Unauthorized, (result as Result.Error).failure)
        coVerify { mockLocal.getAccessToken() }
        verify { mockJwtDecoder.decodeToken(token) }
    }

    @Test
    fun `should return unexpected error when exception occurs in getCurrentUser`() = runBlocking {
        // Arrange
        val token = "valid_token"
        val exception = RuntimeException("JWT decode error")
        
        coEvery { mockLocal.getAccessToken() } returns token
        every { mockJwtDecoder.decodeToken(token) } throws exception

        // Act
        val result = repository.getCurrentUser()

        // Assert
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.failure is Failure.Unexpected)
        assertEquals(exception, (error.failure as Failure.Unexpected).exception)
        coVerify { mockLocal.getAccessToken() }
        verify { mockJwtDecoder.decodeToken(token) }
    }

    @Test
    fun `should handle user with null name in getCurrentUser`() = runBlocking {
        // Arrange
        val token = "valid_token"
        val jwtPayload = JwtPayload(
            userId = "user123",
            email = "test@example.com",
            name = null,
            role = "user",
            issuedAt = 1234567890L,
            expiresAt = 1234567890L + 3600L
        )
        
        coEvery { mockLocal.getAccessToken() } returns token
        every { mockJwtDecoder.decodeToken(token) } returns jwtPayload

        // Act
        val result = repository.getCurrentUser()

        // Assert
        assertTrue(result is Result.Error) // Should fail because Name.create("") throws validation error
        val error = result as Result.Error
        assertTrue(error.failure is Failure.Unexpected)
        coVerify { mockLocal.getAccessToken() }
        verify { mockJwtDecoder.decodeToken(token) }
    }

    @Test
    fun `should return success with refresh token from local storage`() = runBlocking {
        // Arrange
        val refreshToken = "valid-refresh-token"
        coEvery { mockLocal.getRefreshToken() } returns refreshToken

        // Act
        val result = repository.getRefreshToken()

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(refreshToken, (result as Result.Success).data)
        coVerify { mockLocal.getRefreshToken() }
    }

    @Test
    fun `should return error when no refresh token exists`() = runBlocking {
        // Arrange
        coEvery { mockLocal.getRefreshToken() } returns null

        // Act
        val result = repository.getRefreshToken()

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(AuthFailure.InvalidCredentials, (result as Result.Error).failure)
        coVerify { mockLocal.getRefreshToken() }
    }

    @Test
    fun `should return unexpected error when local storage throws exception`() = runBlocking {
        // Arrange
        val exception = RuntimeException("Storage error")
        coEvery { mockLocal.getRefreshToken() } throws exception

        // Act
        val result = repository.getRefreshToken()

        // Assert
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.failure is Failure.Unexpected)
        assertEquals(exception, (error.failure as Failure.Unexpected).exception)
        coVerify { mockLocal.getRefreshToken() }
    }

    @Test
    fun `should return success on valid refresh session`() = runBlocking {
        // Arrange
        val refreshToken = "valid-refresh-token"
        val refreshRequest = RefreshTokenRequest(refreshToken)
        val loginResponse = LoginResponse(accessToken = "new_access_token", refreshToken = "new_refresh_token")
        
        coEvery { mockRemote.refreshToken(refreshRequest) } returns loginResponse
        coEvery { mockLocal.saveAccessToken("new_access_token") } just Runs
        coEvery { mockLocal.saveRefreshToken("new_refresh_token") } just Runs

        // Act
        val result = repository.refreshSession(refreshToken)

        // Assert
        assertTrue(result is Result.Success)
        coVerify { 
            mockRemote.refreshToken(refreshRequest)
            mockLocal.saveAccessToken("new_access_token")
            mockLocal.saveRefreshToken("new_refresh_token")
        }
    }

    @Test
    fun `should return invalid credentials error on 401 response for refresh session`() = runBlocking {
        // Arrange
        val refreshToken = "valid-refresh-token"
        val refreshRequest = RefreshTokenRequest(refreshToken)
        val httpException = HttpException(Response.error<Any>(401, "".toResponseBody()))
        
        coEvery { mockRemote.refreshToken(refreshRequest) } throws httpException

        // Act
        val result = repository.refreshSession(refreshToken)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(AuthFailure.InvalidCredentials, (result as Result.Error).failure)
        coVerify { mockRemote.refreshToken(refreshRequest) }
        coVerify(exactly = 0) { 
            mockLocal.saveAccessToken(any())
            mockLocal.saveRefreshToken(any())
        }
    }

    @Test
    fun `should return server error on other http errors for refresh session`() = runBlocking {
        // Arrange
        val refreshToken = "valid-refresh-token"
        val refreshRequest = RefreshTokenRequest(refreshToken)
        val httpException = HttpException(Response.error<Any>(500, "".toResponseBody()))
        
        coEvery { mockRemote.refreshToken(refreshRequest) } throws httpException

        // Act
        val result = repository.refreshSession(refreshToken)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(Failure.ServerError, (result as Result.Error).failure)
        coVerify { mockRemote.refreshToken(refreshRequest) }
        coVerify(exactly = 0) { 
            mockLocal.saveAccessToken(any())
            mockLocal.saveRefreshToken(any())
        }
    }

    @Test
    fun `should return local error on IOException for refresh session`() = runBlocking {
        // Arrange
        val refreshToken = "valid-refresh-token"
        val refreshRequest = RefreshTokenRequest(refreshToken)
        
        coEvery { mockRemote.refreshToken(refreshRequest) } throws IOException("Error")

        // Act
        val result = repository.refreshSession(refreshToken)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(Failure.LocalError, (result as Result.Error).failure)
        coVerify { mockRemote.refreshToken(refreshRequest) }
        coVerify(exactly = 0) { 
            mockLocal.saveAccessToken(any())
            mockLocal.saveRefreshToken(any())
        }
    }

    @Test
    fun `should return unexpected error on other exceptions for refresh session`() = runBlocking {
        // Arrange
        val refreshToken = "valid-refresh-token"
        val refreshRequest = RefreshTokenRequest(refreshToken)
        val exception = RuntimeException("Unexpected error")
        
        coEvery { mockRemote.refreshToken(refreshRequest) } throws exception

        // Act
        val result = repository.refreshSession(refreshToken)

        // Assert
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.failure is Failure.Unexpected)
        assertEquals(exception, (error.failure as Failure.Unexpected).exception)
        coVerify { mockRemote.refreshToken(refreshRequest) }
        coVerify(exactly = 0) { 
            mockLocal.saveAccessToken(any())
            mockLocal.saveRefreshToken(any())
        }
    }

    @Test
    fun `should return success with pre register info when user data exists`() = runBlocking {
        // Arrange
        val name = "João Silva"
        val email = "joao@example.com"
        val isFirstAccess = false
        coEvery { mockUserLocal.getUserName() } returns name
        coEvery { mockUserLocal.getUserEmail() } returns email
        coEvery { mockUserLocal.getIsFirstAccess() } returns isFirstAccess

        // Act
        val result = repository.getPreRegisterInfo()

        // Assert
        assertTrue(result is Result.Success)
        val preRegisterInfo = (result as Result.Success).data
        assertEquals(name, preRegisterInfo.name)
        assertEquals(email, preRegisterInfo.email)
        assertEquals(isFirstAccess, preRegisterInfo.isFirstAccess)
        coVerify { 
            mockUserLocal.getUserName()
            mockUserLocal.getUserEmail()
            mockUserLocal.getIsFirstAccess()
        }
    }

    @Test
    fun `should return success with first access true when no user data exists`() = runBlocking {
        // Arrange
        val isFirstAccess = true
        coEvery { mockUserLocal.getUserName() } returns null
        coEvery { mockUserLocal.getUserEmail() } returns null
        coEvery { mockUserLocal.getIsFirstAccess() } returns isFirstAccess

        // Act
        val result = repository.getPreRegisterInfo()

        // Assert
        assertTrue(result is Result.Success)
        val preRegisterInfo = (result as Result.Success).data
        assertEquals(null, preRegisterInfo.name)
        assertEquals(null, preRegisterInfo.email)
        assertEquals(isFirstAccess, preRegisterInfo.isFirstAccess)
        coVerify { 
            mockUserLocal.getUserName()
            mockUserLocal.getUserEmail()
            mockUserLocal.getIsFirstAccess()
        }
    }

    @Test
    fun `should return success with partial data when only name exists`() = runBlocking {
        // Arrange
        val name = "João Silva"
        val isFirstAccess = false
        coEvery { mockUserLocal.getUserName() } returns name
        coEvery { mockUserLocal.getUserEmail() } returns null
        coEvery { mockUserLocal.getIsFirstAccess() } returns isFirstAccess

        // Act
        val result = repository.getPreRegisterInfo()

        // Assert
        assertTrue(result is Result.Success)
        val preRegisterInfo = (result as Result.Success).data
        assertEquals(name, preRegisterInfo.name)
        assertEquals(null, preRegisterInfo.email)
        assertEquals(isFirstAccess, preRegisterInfo.isFirstAccess)
        coVerify { 
            mockUserLocal.getUserName()
            mockUserLocal.getUserEmail()
            mockUserLocal.getIsFirstAccess()
        }
    }

    @Test
    fun `should return success with partial data when only email exists`() = runBlocking {
        // Arrange
        val email = "joao@example.com"
        val isFirstAccess = false
        coEvery { mockUserLocal.getUserName() } returns null
        coEvery { mockUserLocal.getUserEmail() } returns email
        coEvery { mockUserLocal.getIsFirstAccess() } returns isFirstAccess

        // Act
        val result = repository.getPreRegisterInfo()

        // Assert
        assertTrue(result is Result.Success)
        val preRegisterInfo = (result as Result.Success).data
        assertEquals(null, preRegisterInfo.name)
        assertEquals(email, preRegisterInfo.email)
        assertEquals(isFirstAccess, preRegisterInfo.isFirstAccess)
        coVerify { 
            mockUserLocal.getUserName()
            mockUserLocal.getUserEmail()
            mockUserLocal.getIsFirstAccess()
        }
    }

    @Test
    fun `should return unexpected error when getUserName throws exception`() = runBlocking {
        // Arrange
        val exception = RuntimeException("Storage error")
        coEvery { mockUserLocal.getUserName() } throws exception

        // Act
        val result = repository.getPreRegisterInfo()

        // Assert
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.failure is Failure.Unexpected)
        assertEquals(exception, (error.failure as Failure.Unexpected).exception)
        coVerify { mockUserLocal.getUserName() }
        coVerify(exactly = 0) { 
            mockUserLocal.getUserEmail()
            mockUserLocal.getIsFirstAccess()
        }
    }

    @Test
    fun `should return unexpected error when getIsFirstAccess throws exception`() = runBlocking {
        // Arrange
        val exception = RuntimeException("Storage error")
        coEvery { mockUserLocal.getUserName() } returns "João"
        coEvery { mockUserLocal.getUserEmail() } returns "joao@example.com"
        coEvery { mockUserLocal.getIsFirstAccess() } throws exception

        // Act
        val result = repository.getPreRegisterInfo()

        // Assert
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.failure is Failure.Unexpected)
        assertEquals(exception, (error.failure as Failure.Unexpected).exception)
        coVerify { 
            mockUserLocal.getUserName()
            mockUserLocal.getUserEmail()
            mockUserLocal.getIsFirstAccess()
        }
    }
} 