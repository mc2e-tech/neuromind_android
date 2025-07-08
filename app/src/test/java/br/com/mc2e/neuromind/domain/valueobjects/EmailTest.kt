package br.com.mc2e.neuromind.domain.valueObjects

import br.com.mc2e.neuromind.domain.failures.ValidationException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class EmailTest {
    @Test
    fun `create should throw EmailValidationException when email is invalid`() {
        val invalidEmails = listOf(
            "",
            "   ",
            "invalid",
            "test@",
            "@example.com",
            "test@example",
            "test@.com",
            "test@example.",
            "test@-example.com",
            "test@example-.com",
            "test..name@example.com",
            ".test@example.com",
            "test.@example.com",
            "test@example..com",
            "test@.example.com",
            "test@example.com.",
            "test@example.c",
            "test@example.1"
        )

        invalidEmails.forEach { email ->
            assertThrows(ValidationException.EmailValidationException::class.java) {
                Email.create(email)
            }
        }
    }

    @Test
    fun `create should return Email when email is valid`() {
        val validEmails = listOf(
            "test@example.com",
            "test.name@example.com",
            "test+name@example.com",
            "test@sub.example.com",
            "test@example.co.uk",
            "test@example-domain.com"
        )

        validEmails.forEach { email ->
            val emailValue = Email.create(email)
            assertEquals(email.lowercase(), emailValue.getValue())
        }
    }

    @Test
    fun `create should normalize email to lowercase`() {
        val email = Email.create("Test@Example.com")
        assertEquals("test@example.com", email.getValue())
    }

    @Test
    fun `create should trim whitespace`() {
        val email = Email.create("  test@example.com  ")
        assertEquals("test@example.com", email.getValue())
    }

    @Test
    fun `toString should return email value`() {
        val email = Email.create("test@example.com")
        assertEquals("test@example.com", email.toString())
    }
} 