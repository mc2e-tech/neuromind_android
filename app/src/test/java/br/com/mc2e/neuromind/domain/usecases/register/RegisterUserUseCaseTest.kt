package br.com.mc2e.neuromind.domain.usecases.register

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.repositories.UserRepository
import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.valueObjects.Name
import br.com.mc2e.neuromind.domain.valueObjects.Password
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RegisterUserUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var registerUserUseCase: RegisterUserUseCase

    @Before
    fun setup() {
        userRepository = mockk()
        registerUserUseCase = RegisterUserUseCase(userRepository)
    }

    @Test
    fun `invoke should return success when user is registered successfully`() = runBlocking {
        // Given
        val input = RegisterUserInput(
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com"),
            password = Password.create("SecurePassword1!")
        )
        coEvery { userRepository.register(input) } returns Result.Success(Unit)

        // When
        val result = registerUserUseCase(input)

        // Then
        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)
    }

    @Test
    fun `invoke should return error when repository throws unexpected exception`() = runBlocking {
        // Given
        val input = RegisterUserInput(
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com"),
            password = Password.create("SecurePassword1!")
        )
        val exception = RuntimeException("Database failure")
        coEvery { userRepository.register(input) } throws exception

        // When
        val result = registerUserUseCase(input)

        // Then
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is Failure.Unexpected)
        assertEquals(exception, (result.failure as Failure.Unexpected).exception)
    }
}