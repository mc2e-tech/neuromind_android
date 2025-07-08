package br.com.mc2e.neuromind.domain.usecases.register

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.ValidationFailure
import br.com.mc2e.neuromind.domain.repositories.UserRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class SaveValidUserNameUseCaseTest {

    private lateinit var useCase: SaveValidUserNameUseCase
    private val userRepository = mockk<UserRepository>()

    @Before
    fun setUp() {
        useCase = SaveValidUserNameUseCase(userRepository)
    }

    @Test
    fun `should return Success when name is valid`() = runBlocking {
        val validName = "John Smith"
        coEvery { userRepository.saveUserName(validName) } just Runs

        val result = useCase.execute(validName)

        assertTrue(result is Result.Success)
        val name = (result as Result.Success).data
        assertEquals(validName, name.getValue())
        coVerify(exactly = 1) { userRepository.saveUserName(validName) }
    }

    @Test
    fun `should return Error InvalidName when name is blank`() = runBlocking {
        val blankName = "   "
        val result = useCase.execute(blankName)
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is ValidationFailure.InvalidName)
    }

    @Test
    fun `should return Error InvalidName when name is too short`() = runBlocking {
        val shortName = "Jon"
        val result = useCase.execute(shortName)
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is ValidationFailure.InvalidName)
    }

    @Test
    fun `should return Error InvalidName when name is too long`() = runBlocking {
        val longName = "A".repeat(101)
        val result = useCase.execute(longName)
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is ValidationFailure.InvalidName)
    }

    @Test
    fun `should return Error InvalidName when name has invalid characters`() = runBlocking {
        val invalidName = "John123 Smith"
        val result = useCase.execute(invalidName)
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is ValidationFailure.InvalidName)
    }

    @Test
    fun `should return Error InvalidCompleteName when name is incomplete`() = runBlocking {
        val incompleteName = "John"
        val result = useCase.execute(incompleteName)
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is ValidationFailure.InvalidCompleteName)
    }

    @Test
    fun `should return Success for names with allowed special characters`() = runBlocking {
        val validNames = listOf(
            "John-Paul Smith",
            "Maria's Santos",
            "José Carlos-Santos"
        )

        validNames.forEach { name ->
            coEvery { userRepository.saveUserName(name) } just Runs
            val result = useCase.execute(name)
            assertTrue(result is Result.Success)
            assertEquals(name, (result as Result.Success).data.getValue())
            coVerify { userRepository.saveUserName(name) }
        }
    }

    @Test
    fun `should not call repository when name is invalid`() = runBlocking {
        val invalidName = "@@@"
        val result = useCase.execute(invalidName)
        assertTrue(result is Result.Error)
        assertTrue((result as Result.Error).failure is ValidationFailure.InvalidName)
        coVerify(exactly = 0) { userRepository.saveUserName(any()) }
    }
}