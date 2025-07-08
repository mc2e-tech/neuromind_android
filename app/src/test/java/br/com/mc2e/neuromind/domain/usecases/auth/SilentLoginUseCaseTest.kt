package br.com.mc2e.neuromind.domain.usecases.auth

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.entities.PreRegisterInfo
import br.com.mc2e.neuromind.domain.failures.AuthFailure
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.repositories.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SilentLoginUseCaseTest {
    private lateinit var authRepository: AuthRepository
    private lateinit var silentLoginUseCase: SilentLoginUseCase

    @Before
    fun setup() {
        authRepository = mockk()
        silentLoginUseCase = SilentLoginUseCase(authRepository)
    }

    @Test
    fun `should return success when silent login is successful`() = runBlocking {
        val refreshToken = "valid-refresh-token"
        coEvery { authRepository.getRefreshToken() } returns Result.Success(refreshToken)
        coEvery { authRepository.refreshSession(refreshToken) } returns Result.Success(Unit)

        val result = silentLoginUseCase()
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
    }

    @Test
    fun `should return Failure_Unauthorized when refresh session fails`() = runBlocking {
        val refreshToken = "valid-refresh-token"
        coEvery { authRepository.getRefreshToken() } returns Result.Success(refreshToken)
        coEvery { authRepository.refreshSession(refreshToken) } returns Result.Error(AuthFailure.InvalidCredentials)

        val result = silentLoginUseCase()
        assertTrue(result is Result.Error)
        assertEquals(Failure.Unauthorized, (result as Result.Error).failure)
    }

    // Fallback: getRefreshToken falha
    @Test
    fun `should return Failure_Unexpected when getPreRegisterInfo fails in fallback`() = runBlocking {
        coEvery { authRepository.getRefreshToken() } returns Result.Error(AuthFailure.InvalidCredentials)
        coEvery { authRepository.getPreRegisterInfo() } returns Result.Error(Failure.Unexpected(RuntimeException("Test error")))

        val result = silentLoginUseCase()
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is Failure.Unexpected)
    }

    @Test
    fun `should return UserFirstAccess when isFirstAccess is true in fallback`() = runBlocking {
        coEvery { authRepository.getRefreshToken() } returns Result.Error(AuthFailure.InvalidCredentials)
        val preRegisterInfo = PreRegisterInfo(name = null, email = null, isFirstAccess = true)
        coEvery { authRepository.getPreRegisterInfo() } returns Result.Success(preRegisterInfo)

        val result = silentLoginUseCase()
        assertTrue(result is Result.Error)
        assertEquals(AuthFailure.UserFirstAccess, (result as Result.Error).failure)
    }

    @Test
    fun `should return RegisterStarted when name is not null in fallback`() = runBlocking {
        coEvery { authRepository.getRefreshToken() } returns Result.Error(AuthFailure.InvalidCredentials)
        val preRegisterInfo = PreRegisterInfo(name = "Joao", email = null, isFirstAccess = false)
        coEvery { authRepository.getPreRegisterInfo() } returns Result.Success(preRegisterInfo)

        val result = silentLoginUseCase()
        assertTrue(result is Result.Error)
        val failure = (result as Result.Error).failure
        assertTrue(failure is AuthFailure.RegisterStarted)
        assertEquals("Joao", (failure as AuthFailure.RegisterStarted).name)
        assertEquals(null, failure.email)
    }

    @Test
    fun `should return RegisterStarted when email is not null in fallback`() = runBlocking {
        coEvery { authRepository.getRefreshToken() } returns Result.Error(AuthFailure.InvalidCredentials)
        val preRegisterInfo = PreRegisterInfo(name = null, email = "joao@email.com", isFirstAccess = false)
        coEvery { authRepository.getPreRegisterInfo() } returns Result.Success(preRegisterInfo)

        val result = silentLoginUseCase()
        assertTrue(result is Result.Error)
        val failure = (result as Result.Error).failure
        assertTrue(failure is AuthFailure.RegisterStarted)
        assertEquals(null, (failure as AuthFailure.RegisterStarted).name)
        assertEquals("joao@email.com", failure.email)
    }

    @Test
    fun `should return RegisterStarted when both name and email are not null in fallback`() = runBlocking {
        coEvery { authRepository.getRefreshToken() } returns Result.Error(AuthFailure.InvalidCredentials)
        val preRegisterInfo = PreRegisterInfo(name = "Joao", email = "joao@email.com", isFirstAccess = false)
        coEvery { authRepository.getPreRegisterInfo() } returns Result.Success(preRegisterInfo)

        val result = silentLoginUseCase()
        assertTrue(result is Result.Error)
        val failure = (result as Result.Error).failure
        assertTrue(failure is AuthFailure.RegisterStarted)
        assertEquals("Joao", (failure as AuthFailure.RegisterStarted).name)
        assertEquals("joao@email.com", failure.email)
    }

    @Test
    fun `should return Failure_Unauthorized when fallback has no name, no email, and isFirstAccess false`() = runBlocking {
        coEvery { authRepository.getRefreshToken() } returns Result.Error(AuthFailure.InvalidCredentials)
        val preRegisterInfo = PreRegisterInfo(name = null, email = null, isFirstAccess = false)
        coEvery { authRepository.getPreRegisterInfo() } returns Result.Success(preRegisterInfo)

        val result = silentLoginUseCase()
        assertTrue(result is Result.Error)
        assertEquals(Failure.Unauthorized, (result as Result.Error).failure)
    }
} 