package br.com.mc2e.neuromind.domain.validations

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class TokenValidatorTest {
    private lateinit var tokenValidator: TokenValidator

    @Before
    fun setup() {
        tokenValidator = TokenValidator()
    }

    @Test
    fun `isExpired should return true when token is null`() {
        // When
        val result = tokenValidator.isExpired(null)

        // Then
        assertTrue(result)
    }

    @Test
    fun `isExpired should return true when token is empty`() {
        // When
        val result = tokenValidator.isExpired("")

        // Then
        assertTrue(result)
    }

    @Test
    fun `isExpired should return true when token is blank`() {
        // When
        val result = tokenValidator.isExpired("   ")

        // Then
        assertTrue(result)
    }

    @Test
    fun `isExpired should return false when token is valid`() {
        // When
        val result = tokenValidator.isExpired("valid-token")

        // Then
        assertFalse(result)
    }
} 