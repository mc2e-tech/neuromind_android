package br.com.mc2e.neuromind.domain.validations

import br.com.mc2e.neuromind.domain.failures.ValidationException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test


class NameValidatorTest {

    @Test
    fun `validate should throw NameValidationException when name is blank`() {
        val exception = assertThrows(ValidationException.NameValidationException::class.java) {
            NameValidator.validate("   ")
        }
        assertEquals("Name is required", exception.message)
    }

    @Test
    fun `validate should throw NameValidationException when name is too short`() {
        val exception = assertThrows(ValidationException.NameValidationException::class.java) {
            NameValidator.validate("Ana")
        }
        assertEquals("Name must be at least 4 characters short", exception.message)
    }

    @Test
    fun `validate should throw NameValidationException when name is too long`() {
        val longName = "A".repeat(101)
        val exception = assertThrows(ValidationException.NameValidationException::class.java) {
            NameValidator.validate(longName)
        }
        assertEquals("Name must be at most 100 characters long", exception.message)
    }

    @Test
    fun `validate should throw NameValidationException when name contains invalid characters`() {
        val exception = assertThrows(ValidationException.NameValidationException::class.java) {
            NameValidator.validate("John@Doe")
        }
        assertEquals("Name contains invalid characters. Only letters, spaces, hyphens, and apostrophes are allowed", exception.message)
    }

    @Test
    fun `validate should throw CompleteNameValidationException when name has only first name`() {
        val exception = assertThrows(ValidationException.CompleteNameValidationException::class.java) {
            NameValidator.validate("John")
        }
        assertEquals("", exception.message)
    }

    @Test
    fun `validate should not throw when name is valid`() {
        NameValidator.validate("Ana Clara")
    }

    @Test
    fun `validate should not throw when name contains accents hyphen and apostrophe`() {
        NameValidator.validate("João D'Ávila Silva")
    }
}