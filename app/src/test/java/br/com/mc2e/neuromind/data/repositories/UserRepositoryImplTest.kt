package br.com.mc2e.neuromind.data.repositories

import br.com.mc2e.neuromind.data.datasources.local.UserLocalDataSource
import br.com.mc2e.neuromind.data.datasources.remote.AuthRemoteDataSource
import br.com.mc2e.neuromind.data.datasources.remote.models.RegisterRequest
import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.valueObjects.Name
import br.com.mc2e.neuromind.domain.valueObjects.Password
import br.com.mc2e.neuromind.domain.usecases.register.RegisterUserInput
import io.mockk.*
import org.junit.Assert.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class UserRepositoryImplTest {
    private lateinit var repository: UserRepositoryImpl
    private val remoteDataSource = mockk<AuthRemoteDataSource>()
    private val localDataSource = mockk<UserLocalDataSource>()


    @Before
    fun setup() {
        repository = UserRepositoryImpl(remoteDataSource, localDataSource)
    }

    @Test
    fun `register should return Success when remote call succeeds`() = runBlocking {
        val input = RegisterUserInput(
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com"),
            password = Password.create("securePassword123!")
        )

        coEvery { remoteDataSource.register(any()) } returns Unit

        val result = repository.register(input)

        assertTrue(result is Result.Success)
        assertEquals(Unit, (result as Result.Success).data)

        coVerify(exactly = 1) {
            remoteDataSource.register(
                RegisterRequest(
                    name = "João Silva",
                    email = "joao@example.com",
                    password = "securePassword123!"
                )
            )
        }
    }

    @Test
    fun `register should return Error when remote throws exception`() = runBlocking {
        val input = RegisterUserInput(
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com"),
            password = Password.create("securePassword123!")
        )

        val exception = RuntimeException("Network error")
        coEvery { remoteDataSource.register(any()) } throws exception

        val result = repository.register(input)

        assertTrue(result is Result.Error)
    }

    @Test
    fun `saveUserName should call localDataSource`() = runBlocking {
        coEvery { localDataSource.saveUserName("João") } just Runs

        repository.saveUserName("João")

        coVerify { localDataSource.saveUserName("João") }
    }

    @Test
    fun `getUserName should return value from localDataSource`() = runBlocking {
        coEvery { localDataSource.getUserName() } returns "João"

        val result = repository.getUserName()

        assertEquals("João", result)
    }

    @Test
    fun `saveUserEmail should call localDataSource`() = runBlocking {
        coEvery { localDataSource.saveUserEmail("joao@example.com") } just Runs

        repository.saveUserEmail("joao@example.com")

        coVerify { localDataSource.saveUserEmail("joao@example.com") }
    }

    @Test
    fun `getUserEmail should return value from localDataSource`() = runBlocking {
        coEvery { localDataSource.getUserEmail() } returns "joao@example.com"

        val result = repository.getUserEmail()

        assertEquals("joao@example.com", result)
    }
}