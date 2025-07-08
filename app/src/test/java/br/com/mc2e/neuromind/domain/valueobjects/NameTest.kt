package br.com.mc2e.neuromind.domain.valueObjects

import br.com.mc2e.neuromind.domain.failures.ValidationException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class NameTest {

    @Test
    fun `create should return Name for valid names`() {
        val validNames = listOf(
            "John Smith",
            "Maria Santos",
            "José Carlos",
            "Anna Paula",
            "Peter Henry"
        )
        validNames.forEach { name ->
            val nameValue = Name.create(name)
            assertEquals(name.trim(), nameValue.getValue())
        }
    }

    @Test
    fun `create should throw NameValidationException for empty name`() {
        val exception = assertThrows(ValidationException.NameValidationException::class.java) {
            Name.create("")
        }
        assertEquals("Name is required", exception.message)
    }

    @Test
    fun `create should throw NameValidationException for blank name`() {
        val exception = assertThrows(ValidationException.NameValidationException::class.java) {
            Name.create("   ")
        }
        assertEquals("Name is required", exception.message)
    }

    @Test
    fun `create should throw NameValidationException for short name`() {
        val exception = assertThrows(ValidationException.NameValidationException::class.java) {
            Name.create("Jo")
        }
        assertEquals("Name must be at least 4 characters short", exception.message)
    }

    @Test
    fun `create should throw CompleteNameValidationException for single name`() {
        val exception = assertThrows(ValidationException.CompleteNameValidationException::class.java) {
            Name.create("John")
        }
        assertEquals("", exception.message)
    }

    @Test
    fun `create should throw NameValidationException for long name`() {
        val longName = "A".repeat(101)
        val exception = assertThrows(ValidationException.NameValidationException::class.java) {
            Name.create(longName)
        }
        assertEquals("Name must be at most 100 characters long", exception.message)
    }

    @Test
    fun `create should throw NameValidationException for name with invalid characters`() {
        val exception = assertThrows(ValidationException.NameValidationException::class.java) {
            Name.create("John123 Smith")
        }
        assertEquals("Name contains invalid characters. Only letters, spaces, hyphens, and apostrophes are allowed", exception.message)
    }

    @Test
    fun `create should trim whitespace from valid names`() {
        val nameWithSpaces = "  John Smith  "
        val nameValue = Name.create(nameWithSpaces)
        assertEquals("John Smith", nameValue.getValue())
    }

    @Test
    fun `create should return Name for names with allowed special characters`() {
        val namesWithSpecialChars = listOf(
            "John-Paul Smith",
            "Maria's Santos",
            "José Carlos-Santos"
        )
        namesWithSpecialChars.forEach { name ->
            val nameValue = Name.create(name)
            assertEquals(name.trim(), nameValue.getValue())
        }
    }
}