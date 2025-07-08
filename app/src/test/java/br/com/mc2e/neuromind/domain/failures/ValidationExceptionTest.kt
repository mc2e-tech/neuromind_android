package br.com.mc2e.neuromind.domain.failures

import org.junit.Assert.*
import org.junit.Test

class ValidationExceptionTest {

    @Test
    fun `should create EmailValidationException with message`() {
        val message = "Invalid email format"
        val exception = ValidationException.EmailValidationException(message)
        
        assertEquals(message, exception.message)
        assertTrue(exception is ValidationException)
        assertTrue(exception is Exception)
    }

    @Test
    fun `should create PasswordValidationException with message`() {
        val message = "Password too weak"
        val exception = ValidationException.PasswordValidationException(message)
        
        assertEquals(message, exception.message)
        assertTrue(exception is ValidationException)
        assertTrue(exception is Exception)
    }

    @Test
    fun `should create NameValidationException with message`() {
        val message = "Name too short"
        val exception = ValidationException.NameValidationException(message)
        
        assertEquals(message, exception.message)
        assertTrue(exception is ValidationException)
        assertTrue(exception is Exception)
    }

    @Test
    fun `should create CompleteNameValidationException with default message`() {
        val exception = ValidationException.CompleteNameValidationException()
        
        assertEquals("", exception.message)
        assertTrue(exception is ValidationException)
        assertTrue(exception is Exception)
    }

    @Test
    fun `should allow throwing and catching ValidationException`() {
        val message = "Test validation error"
        val exception = ValidationException.EmailValidationException(message)
        
        try {
            throw exception
        } catch (e: ValidationException) {
            assertEquals(message, e.message)
        }
    }

    @Test
    fun `should allow throwing and catching specific ValidationException types`() {
        val emailException = ValidationException.EmailValidationException("Email error")
        val passwordException = ValidationException.PasswordValidationException("Password error")
        
        try {
            throw emailException
        } catch (e: ValidationException.EmailValidationException) {
            assertEquals("Email error", e.message)
        }
        
        try {
            throw passwordException
        } catch (e: ValidationException.PasswordValidationException) {
            assertEquals("Password error", e.message)
        }
    }
} 