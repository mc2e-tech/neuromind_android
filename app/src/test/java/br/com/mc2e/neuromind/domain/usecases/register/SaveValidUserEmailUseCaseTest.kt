package br.com.mc2e.neuromind.domain.usecases.register

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.ValidationFailure
import br.com.mc2e.neuromind.domain.failures.ValidationException
import br.com.mc2e.neuromind.domain.repositories.UserRepository
import br.com.mc2e.neuromind.domain.valueObjects.Email
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SaveValidUserEmailUseCaseTest {

    private lateinit var userRepository: UserRepository
    private lateinit var useCase: SaveValidUserEmailUseCase

    @Before
    fun setup() {
        userRepository = mockk()
        useCase = SaveValidUserEmailUseCase(userRepository)
    }


    @Test
    fun `should return Success when email is valid and saved`() = runBlocking {
        val validEmail = "john.doe@email.com"
        coEvery { userRepository.saveUserEmail(validEmail) } just Runs

        val result = useCase.execute(validEmail)

        assertTrue(result is Result.Success)
        val email = (result as Result.Success).data
        assertEquals(validEmail, email.getValue())

        coVerify(exactly = 1) { userRepository.saveUserEmail(validEmail) }
    }

    @Test
    fun `should return Error when email is invalid`() = runBlocking {
        val invalidEmail = "invalid-email"

        val result = useCase.execute(invalidEmail)

        assertTrue(result is Result.Error)
        val failure = (result as Result.Error).failure
        assertTrue(failure is ValidationFailure.InvalidEmail)
    }

    @Test
    fun `should return Error when email is blank`() = runBlocking {
        val blankEmail = "   "

        val result = useCase.execute(blankEmail)

        assertTrue(result is Result.Error)
        val failure = (result as Result.Error).failure
        assertTrue(failure is ValidationFailure.InvalidEmail)
    }

    @Test
    fun `should return Error when email is too long`() = runBlocking {
        val emailTooLong = "a".repeat(256) + "@example.com"

        val result = useCase.execute(emailTooLong)

        assertTrue(result is Result.Error)
        val failure = (result as Result.Error).failure
        assertTrue(failure is ValidationFailure.InvalidEmail)
    }

    @Test
    fun `should return Error with default message when EmailValidationException has null message`() =
        runBlocking {
            // Arrange
            mockkObject(Email)
            every { Email.create("bad@email.com") } throws ValidationException.EmailValidationException(
                ""
            )

            // Act
            val result = useCase.execute("bad@email.com")

            // Assert
            assertTrue(result is Result.Error)
            val failure = (result as Result.Error).failure
            assertTrue(failure is ValidationFailure.InvalidEmail)

            unmockkAll()
        }
}
