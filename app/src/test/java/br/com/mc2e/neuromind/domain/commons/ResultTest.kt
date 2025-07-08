package br.com.mc2e.neuromind.domain.commons

import br.com.mc2e.neuromind.domain.failures.Failure
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultTest {

    @Test
    fun `Success should have correct data and properties`() {
        // Given
        val testData = "test data"
        val result = Result.Success(testData)

        // Then
        assertEquals(testData, result.data)
        assertTrue(result.isSuccess)
        assertFalse(result.isError)
        assertEquals(testData, result.getOrNull())
        assertNull(result.failureOrNull())
    }

    @Test
    fun `Error should have correct failure and properties`() {
        // Given
        val failure = Failure.Unexpected(Exception("test"))
        val result = Result.Error(failure)

        // Then
        assertEquals(failure, result.failure)
        assertFalse(result.isSuccess)
        assertTrue(result.isError)
        assertNull(result.getOrNull())
        assertEquals(failure, result.failureOrNull())
    }

    @Test
    fun `getOrNull should return data for Success`() {
        // Given
        val testData = 42
        val result = Result.Success(testData)

        // When
        val resultData = result.getOrNull()

        // Then
        assertEquals(testData, resultData)
    }

    @Test
    fun `getOrNull should return null for Error`() {
        // Given
        val failure = Failure.Unexpected(Exception("test"))
        val result = Result.Error(failure)

        // When
        val resultData = result.getOrNull()

        // Then
        assertNull(resultData)
    }

    @Test
    fun `failureOrNull should return null for Success`() {
        // Given
        val testData = "success data"
        val result = Result.Success(testData)

        // When
        val failure = result.failureOrNull()

        // Then
        assertNull(failure)
    }

    @Test
    fun `failureOrNull should return failure for Error`() {
        // Given
        val failure = Failure.Unexpected(Exception("test"))
        val result = Result.Error(failure)

        // When
        val resultFailure = result.failureOrNull()

        // Then
        assertEquals(failure, resultFailure)
    }

    @Test
    fun `Success with null data should work correctly`() {
        // Given
        val result = Result.Success<String?>(null)

        // Then
        assertTrue(result.isSuccess)
        assertFalse(result.isError)
        assertNull(result.getOrNull())
        assertNull(result.failureOrNull())
    }

    @Test
    fun `Success with complex data should work correctly`() {
        // Given
        data class TestData(val id: Int, val name: String)
        val testData = TestData(1, "test")
        val result = Result.Success(testData)

        // Then
        assertEquals(testData, result.data)
        assertTrue(result.isSuccess)
        assertFalse(result.isError)
        assertEquals(testData, result.getOrNull())
        assertNull(result.failureOrNull())
    }

    @Test
    fun `Error with different failure types should work correctly`() {
        // Given
        val failures = listOf(
            Failure.ServerError,
            Failure.LocalError,
            Failure.Unexpected(Exception("test"))
        )

        failures.forEach { failure ->
            // When
            val result = Result.Error(failure)

            // Then
            assertEquals(failure, result.failure)
            assertFalse(result.isSuccess)
            assertTrue(result.isError)
            assertNull(result.getOrNull())
            assertEquals(failure, result.failureOrNull())
        }
    }

    @Test
    fun `Error with data object failures should work correctly`() {
        // Given
        val failures = listOf(
            Failure.ServerError,
            Failure.Unauthorized,
            Failure.NotFound
        )

        failures.forEach { failure ->
            // When
            val result = Result.Error(failure)

            // Then
            assertEquals(failure, result.failure)
            assertFalse(result.isSuccess)
            assertTrue(result.isError)
            assertNull(result.getOrNull())
            assertEquals(failure, result.failureOrNull())
        }
    }
}