package br.com.mc2e.neuromind.domain.usecases.auth

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.entities.User
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.repositories.AuthRepository
import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.valueObjects.Name
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetCurrentUserUseCaseTest {
    private lateinit var useCase: GetCurrentUserUseCase
    private val mockRepository = mockk<AuthRepository>()

    @Before
    fun setUp() {
        useCase = GetCurrentUserUseCase(mockRepository)
    }

    @Test
    fun `should return user on success`() = runBlocking {
        val user = User(
            "id1",
            Name.create("John Doe"),
            Email.create("test@example.com"),
        )
        coEvery { mockRepository.getCurrentUser() } returns Result.Success(user)

        val result = useCase.invoke()
        assertTrue(result is Result.Success)
        assertEquals(user, (result as Result.Success).data)
    }

    @Test
    fun `should return error when repository returns error`() = runBlocking {
        val failure = Failure.Unauthorized
        coEvery { mockRepository.getCurrentUser() } returns Result.Error(failure)

        val result = useCase.invoke()
        assertTrue(result is Result.Error)
        assertEquals(failure, (result as Result.Error).failure)
    }

    @Test
    fun `should return unexpected error when exception is thrown`() = runBlocking {
        val exception = RuntimeException("unexpected")
        coEvery { mockRepository.getCurrentUser() } throws exception

        val result = useCase.invoke()
        assertTrue(result is Result.Error)
        val error = result as Result.Error
        assertTrue(error.failure is Failure.Unexpected)
        assertEquals(exception, (error.failure as Failure.Unexpected).exception)
    }
}