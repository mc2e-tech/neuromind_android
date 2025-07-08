package br.com.mc2e.neuromind.data.datasources.local

import br.com.mc2e.neuromind.infrastructure.local.storage.SecureStorage
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AuthLocalSecureDataSourceTest {
    private lateinit var dataSource: AuthLocalSecureDataSource
    private val mockStorage = mockk<SecureStorage>()

    @Before
    fun setUp() {
        dataSource = AuthLocalSecureDataSourceImpl(mockStorage)
        every { mockStorage.saveString(any(), any()) } just Runs
        every { mockStorage.clear(any()) } just Runs
    }

    @Test
    fun `should save and retrieve access token`() {
        every { mockStorage.getString("access_token") } returns "token123"
        dataSource.saveAccessToken("token123")
        val token = dataSource.getAccessToken()
        assertEquals("token123", token)
    }

    @Test
    fun `should return null if access token not set`() {
        every { mockStorage.getString("access_token") } returns null
        val token = dataSource.getAccessToken()
        assertNull(token)
    }

    @Test
    fun `should save and retrieve refresh token`() {
        every { mockStorage.getString("refresh_token") } returns "refresh123"
        dataSource.saveRefreshToken("refresh123")
        val token = dataSource.getRefreshToken()
        assertEquals("refresh123", token)
    }

    @Test
    fun `should return null if refresh token not set`() {
        every { mockStorage.getString("refresh_token") } returns null
        val token = dataSource.getRefreshToken()
        assertNull(token)
    }

    @Test
    fun `should clear tokens`() {
        dataSource.clearTokens()
        verify { mockStorage.clear("access_token") }
        verify { mockStorage.clear("refresh_token") }
    }

    @Test
    fun `should return true when both tokens are valid`() {
        every { mockStorage.getString("access_token") } returns "access123"
        every { mockStorage.getString("refresh_token") } returns "refresh123"
        val hasValidTokens = dataSource.hasValidTokens()
        assertTrue(hasValidTokens)
    }

    @Test
    fun `should return false when access token is null`() {
        every { mockStorage.getString("access_token") } returns null
        every { mockStorage.getString("refresh_token") } returns "refresh123"
        val hasValidTokens = dataSource.hasValidTokens()
        assertFalse(hasValidTokens)
    }

    @Test
    fun `should return false when refresh token is null`() {
        every { mockStorage.getString("access_token") } returns "access123"
        every { mockStorage.getString("refresh_token") } returns null
        val hasValidTokens = dataSource.hasValidTokens()
        assertFalse(hasValidTokens)
    }

    @Test
    fun `should return false when both tokens are null`() {
        every { mockStorage.getString("access_token") } returns null
        every { mockStorage.getString("refresh_token") } returns null
        val hasValidTokens = dataSource.hasValidTokens()
        assertFalse(hasValidTokens)
    }
} 