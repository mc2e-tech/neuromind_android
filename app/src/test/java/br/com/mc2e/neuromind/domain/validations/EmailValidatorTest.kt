package br.com.mc2e.neuromind.domain.validations

import br.com.mc2e.neuromind.domain.failures.ValidationException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class EmailValidatorTest {
    @Test
    fun `validate should throw EmailValidationException when email is empty`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("")
        }
        assertEquals("Email cannot be empty", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when email is blank`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("   ")
        }
        assertEquals("Email cannot be empty", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when email is too long`() {
        val longEmail = "a".repeat(256) + "@example.com"
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate(longEmail)
        }
        assertEquals("Email cannot be longer than 255 characters", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when local part is only dots`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("...@example.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when local part contains invalid character`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test\u0000@example.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when domain part is empty`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@sub..domain.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when domain is missing`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when email has no at sign`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("userexample.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when email has multiple at signs`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("user@@example.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when email contains spaces`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test @example.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when email has no @`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("testexample.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when email has multiple @`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@example@domain.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when local part starts with dot`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate(".test@example.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when local part ends with dot`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test.@example.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when local part has consecutive dots`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test..test@example.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when local part is empty`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("@example.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when domain starts with hyphen`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@-example.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when domain ends with hyphen`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@example-.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when domain starts with dot`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@.example.com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when domain ends with dot`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@example.com.")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when domain has consecutive dots`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@example..com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when domain part starts with hyphen`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@example.-com")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when domain part ends with hyphen`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@example.com-")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when TLD is too short`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@example.c")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should throw EmailValidationException when TLD contains non-letters`() {
        val exception = assertThrows(ValidationException.EmailValidationException::class.java) {
            EmailValidator.validate("test@example.c0m")
        }
        assertEquals("Invalid email format", exception.message)
    }

    @Test
    fun `validate should not throw when email is valid`() {
        val validEmails = listOf(
            "test@example.com",
            "test.name@example.com",
            "test+name@example.com",
            "test@sub.example.com",
            "test@example.co.uk",
            "test@example-domain.com"
        )

        validEmails.forEach { email ->
            EmailValidator.validate(email)
        }
    }
}