package br.com.mc2e.neuromind.data.datasources.local

import br.com.mc2e.neuromind.infrastructure.local.storage.UserPreferencesStorage
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UserLocalDataSourceImplTest {

    private lateinit var userPreferencesStorage: UserPreferencesStorage
    private lateinit var dataSource: UserLocalDataSourceImpl

    companion object {
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_IS_FIRST_ACCESS = "is_first_access"
    }

    @Before
    fun setUp() {
        userPreferencesStorage = mockk(relaxed = true)
        dataSource = UserLocalDataSourceImpl(userPreferencesStorage)
    }

    @Test
    fun `saveUserName should delegate to UserPreferencesStorage`() = runTest {
        val name = "João Silva"

        dataSource.saveUserName(name)

        coVerify { userPreferencesStorage.save(KEY_USER_NAME, name) }
    }

    @Test
    fun `getUserName should return value from UserPreferencesStorage`() = runTest {
        val expectedName = "Maria Oliveira"
        coEvery { userPreferencesStorage.get<String>(KEY_USER_NAME) } returns expectedName

        val result = dataSource.getUserName()

        assertEquals(expectedName, result)
        coVerify { userPreferencesStorage.get<String>(KEY_USER_NAME) }
    }

    @Test
    fun `saveUserEmail should delegate to UserPreferencesStorage`() = runTest {
        val email = "maria@example.com"

        dataSource.saveUserEmail(email)

        coVerify { userPreferencesStorage.save(KEY_USER_EMAIL, email) }
    }

    @Test
    fun `getUserEmail should return value from UserPreferencesStorage`() = runTest {
        val expectedEmail = "joao@example.com"
        coEvery { userPreferencesStorage.get<String>(KEY_USER_EMAIL) } returns expectedEmail

        val result = dataSource.getUserEmail()

        assertEquals(expectedEmail, result)
        coVerify { userPreferencesStorage.get<String>(KEY_USER_EMAIL) }
    }

    @Test
    fun `saveIsFirstAccess should delegate to UserPreferencesStorage`() = runTest {
        val isFirstAccess = true

        dataSource.saveIsFirstAccess(isFirstAccess)

        coVerify { userPreferencesStorage.save(KEY_IS_FIRST_ACCESS, isFirstAccess) }
    }

    @Test
    fun `getIsFirstAccess should return value from UserPreferencesStorage`() = runTest {
        val expectedIsFirstAccess = false
        coEvery { userPreferencesStorage.get(KEY_IS_FIRST_ACCESS, true) } returns expectedIsFirstAccess

        val result = dataSource.getIsFirstAccess()

        assertEquals(expectedIsFirstAccess, result)
        coVerify { userPreferencesStorage.get(KEY_IS_FIRST_ACCESS, true) }
    }
}
