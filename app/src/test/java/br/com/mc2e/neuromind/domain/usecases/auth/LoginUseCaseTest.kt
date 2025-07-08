package br.com.mc2e.neuromind.domain.usecases.auth

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.AuthFailure
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.failures.ValidationFailure
import br.com.mc2e.neuromind.domain.repositories.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {
    private lateinit var authRepository: AuthRepository
    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setup() {
        authRepository = mockk()
        loginUseCase = LoginUseCase(authRepository)
    }

    @Test
    fun `invoke should return success when login is successful`() = runBlocking {
        // Given
        coEvery { authRepository.login(any(), any()) } returns Result.Success(Unit)

        // When
        val result = loginUseCase("joao@example.com", "Password1!")

        // Then
        assertTrue(result is Result.Success)
        assertTrue((result as Result.Success).data == Unit)
    }

    @Test
    fun `invoke should return error when email is invalid`() = runBlocking {
        // When
        val result = loginUseCase("invalid-email", "Password1!")

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is ValidationFailure.InvalidEmail)
    }

    @Test
    fun `invoke should return error when password is invalid`() = runBlocking {
        // When
        val result = loginUseCase("user@example.com", "password") // sem número, maiúscula, especial

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is ValidationFailure.InvalidPassword)
    }

    @Test
    fun `invoke should return error when credentials are invalid`() = runBlocking {
        // Given
        coEvery { authRepository.login(any(), any()) } returns Result.Error(AuthFailure.InvalidCredentials)

        // When
        val result = loginUseCase("user@example.com", "WrongPassword1!")

        // Then
        assertTrue(result is Result.Error)
        assertEquals(AuthFailure.InvalidCredentials, (result as Result.Error).failure)
    }

    @Test
    fun `invoke should return error when repository throws unexpected exception`() = runBlocking {
        // Given
        coEvery { authRepository.login(any(), any()) } throws RuntimeException("Unexpected error")

        // When
        val result = loginUseCase("user@example.com", "Password1!")

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is Failure.Unexpected)
    }
} 