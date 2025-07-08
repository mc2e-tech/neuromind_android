package br.com.mc2e.neuromind.domain.valueObjects

import br.com.mc2e.neuromind.domain.failures.ValidationException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class PasswordTest {
    @Test
    fun `create should throw PasswordValidationException when password is invalid`() {
        val invalidPasswords = listOf(
            "",
            "   ",
            "short",
            "no-upper-123!",
            "NO-LOWER-123!",
            "No-Numbers!",
            "NoSpecial123",
            "Invalid@#$%^&*()_+{}|:<>?"
        )

        invalidPasswords.forEach { password ->
            assertThrows(ValidationException.PasswordValidationException::class.java) {
                Password.create(password)
            }
        }
    }

    @Test
    fun `create should return Password when password is valid`() {
        val validPasswords = listOf(
            "Password123!",
            "P@ssw0rd",
            "Test123!@#",
            "Complex1Pass!",
            "Abc123!@#$%^&*()_+-=[]{}|;:,.<>?"
        )

        validPasswords.forEach { password ->
            val passwordValue = Password.create(password)
            assertEquals(password, passwordValue.getValue())
        }
    }

    @Test
    fun `create should trim whitespace`() {
        val password = Password.create("  Password123!  ")
        assertEquals("Password123!", password.getValue())
    }

    @Test
    fun `toString should return redacted value`() {
        val password = Password.create("Password123!")
        assertEquals("[REDACTED]", password.toString())
    }
} 