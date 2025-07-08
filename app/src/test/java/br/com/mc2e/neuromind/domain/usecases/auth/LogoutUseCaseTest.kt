package br.com.mc2e.neuromind.domain.usecases.auth

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.repositories.AuthRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class LogoutUseCaseTest {
    
    private lateinit var authRepository: AuthRepository
    private lateinit var logoutUseCase: LogoutUseCase
    
    @Before
    fun setup() {
        authRepository = mockk()
        logoutUseCase = LogoutUseCase(authRepository)
    }
    
    @Test
    fun `invoke should return success when logout is successful`() = runBlocking {
        // Given
        coEvery { authRepository.logout() } returns Result.Success(Unit)
        
        // When
        val result = logoutUseCase()
        
        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
    }
    
    @Test
    fun `invoke should return error when logout fails`() = runBlocking {
        // Given
        val exception = Exception("Logout failed")
        coEvery { authRepository.logout() } returns Result.Error(Failure.Unexpected(exception))
        
        // When
        val result = logoutUseCase()
        
        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is Failure.Unexpected)
    }
    
    @Test
    fun `invoke should return error when repository throws exception`() = runBlocking {
        // Given
        val exception = RuntimeException("Network error")
        coEvery { authRepository.logout() } throws exception
        
        // When
        val result = logoutUseCase()
        
        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is Failure.Unexpected)
    }
} 