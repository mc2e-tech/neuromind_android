package br.com.mc2e.neuromind.infrastructure.local.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import io.mockk.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SecureStorageImplTest {
    private lateinit var storage: SecureStorageImpl
    private val mockContext = mockk<Context>(relaxed = true)
    private val mockSharedPreferences = mockk<SharedPreferences>()
    private val mockEditor = mockk<SharedPreferences.Editor>()

    @Before
    fun setUp() {
        every { mockSharedPreferences.edit() } returns mockEditor
        every { mockEditor.putString(any(), any()) } returns mockEditor
        every { mockEditor.remove(any()) } returns mockEditor
        every { mockEditor.clear() } returns mockEditor
        every { mockEditor.apply() } just Runs
        
        // Mock the static methods using mockkStatic
        mockkStatic(EncryptedSharedPreferences::class)
        mockkStatic(MasterKey::class)
        
        val mockMasterKey = mockk<MasterKey>()
        val mockMasterKeyBuilder = mockk<MasterKey.Builder>()
        
        every { mockMasterKeyBuilder.setKeyScheme(any<MasterKey.KeyScheme>()) } returns mockMasterKeyBuilder
        every { mockMasterKeyBuilder.build() } returns mockMasterKey
        every { mockContext.applicationContext } returns mockContext

        every { 
            EncryptedSharedPreferences.create(
                any<Context>(),
                any<String>(),
                any<MasterKey>(),
                any<EncryptedSharedPreferences.PrefKeyEncryptionScheme>(),
                any<EncryptedSharedPreferences.PrefValueEncryptionScheme>()
            )
        } returns mockSharedPreferences
        
        storage = SecureStorageImpl(mockContext)
    }

    @Test
    fun `should save string to shared preferences`() {
        val key = "test_key"
        val value = "test_value"
        
        storage.saveString(key, value)
        
        verify { mockEditor.putString(key, value) }
        verify { mockEditor.apply() }
    }

    @Test
    fun `should get string from shared preferences`() {
        val key = "test_key"
        val expectedValue = "test_value"
        
        every { mockSharedPreferences.getString(key, null) } returns expectedValue
        
        val result = storage.getString(key)
        
        assertEquals(expectedValue, result)
        verify { mockSharedPreferences.getString(key, null) }
    }

    @Test
    fun `should return null when string not found`() {
        val key = "test_key"
        
        every { mockSharedPreferences.getString(key, null) } returns null
        
        val result = storage.getString(key)
        
        assertNull(result)
        verify { mockSharedPreferences.getString(key, null) }
    }

    @Test
    fun `should clear specific key from shared preferences`() {
        val key = "test_key"
        
        storage.clear(key)
        
        verify { mockEditor.remove(key) }
        verify { mockEditor.apply() }
    }

    @Test
    fun `should clear all data from shared preferences`() {
        storage.clearAll()
        
        verify { mockEditor.clear() }
        verify { mockEditor.apply() }
    }

    @Test
    fun `should implement SecureStorage interface`() {
        assertTrue(storage is SecureStorage)
    }

    @Test
    fun `should handle multiple save operations`() {
        storage.saveString("key1", "value1")
        storage.saveString("key2", "value2")
        
        verify { mockEditor.putString("key1", "value1") }
        verify { mockEditor.putString("key2", "value2") }
        verify(exactly = 2) { mockEditor.apply() }
    }

    @Test
    fun `should handle multiple clear operations`() {
        storage.clear("key1")
        storage.clear("key2")
        
        verify { mockEditor.remove("key1") }
        verify { mockEditor.remove("key2") }
        verify(exactly = 2) { mockEditor.apply() }
    }
} 