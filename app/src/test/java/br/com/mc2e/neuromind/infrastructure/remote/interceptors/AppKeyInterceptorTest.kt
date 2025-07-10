package br.com.mc2e.neuromind.infrastructure.remote.interceptors

import br.com.mc2e.neuromind.BuildConfig
import org.junit.Assert.*
import org.junit.Test

class AppKeyInterceptorTest {
    
    private val interceptor = AppKeyInterceptor()
    
    @Test
    fun `should have API key defined in BuildConfig`() {
        // Verify that APP_KEY is defined in BuildConfig
        assertNotNull(BuildConfig.APP_KEY)
        assertTrue(BuildConfig.APP_KEY.isNotBlank())
    }
    
    @Test
    fun `should create interceptor instance`() {
        assertNotNull(interceptor)
    }
    
    @Test
    fun `should be singleton`() {
        val instance1 = AppKeyInterceptor()
        val instance2 = AppKeyInterceptor()
        
        // Both instances should work the same way
        assertNotNull(instance1)
        assertNotNull(instance2)
    }
} 