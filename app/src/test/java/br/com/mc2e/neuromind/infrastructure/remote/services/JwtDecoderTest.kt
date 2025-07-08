package br.com.mc2e.neuromind.infrastructure.remote.services

import br.com.mc2e.neuromind.infrastructure.remote.models.JwtPayload
import com.squareup.moshi.Moshi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class JwtDecoderTest {

    private lateinit var jwtDecoder: JwtDecoder
    private lateinit var moshi: Moshi

    @Before
    fun setup() {
        moshi = Moshi.Builder().build()
        jwtDecoder = JwtDecoder(moshi)
    }

    @Test
    fun `decodeToken should return null for invalid token`() {
        val result = jwtDecoder.decodeToken("invalid.token.here")
        assertNull(result)
    }

    @Test
    fun `decodeToken should return null for empty token`() {
        val result = jwtDecoder.decodeToken("")
        assertNull(result)
    }

    @Test
    fun `isTokenExpired should return true for null token`() {
        val result = jwtDecoder.isTokenExpired("invalid.token")
        assertTrue(result)
    }

    @Test
    fun `getTokenExpirationTime should return null for invalid token`() {
        val result = jwtDecoder.getTokenExpirationTime("invalid.token")
        assertNull(result)
    }

    @Test
    fun `should decode valid JWT`() {
        // Provide a valid JWT and assert claims
    }

    @Test
    fun `should detect expired token`() {
        // Provide an expired JWT and assert isExpired returns true
    }

    @Test
    fun `should handle invalid token`() {
        // Provide an invalid JWT and assert exception or error
    }
} 