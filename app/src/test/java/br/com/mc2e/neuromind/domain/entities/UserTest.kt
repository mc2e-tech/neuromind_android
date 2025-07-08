package br.com.mc2e.neuromind.domain.entities

import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.valueObjects.Name
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class UserTest {

    @Test
    fun `User should be created with valid data`() {
        // Given
        val id = "user-123"
        val name = Name.create("João Silva")
        val email = Email.create("joao@example.com")

        // When
        val user = User(id, name, email)

        // Then
        assertEquals(id, user.id)
        assertEquals(name, user.name)
        assertEquals(email, user.email)
    }

    @Test
    fun `User should have correct data class properties`() {
        // Given
        val user = User(
            id = "user-456",
            name = Name.create("Maria Santos"),
            email = Email.create("maria@example.com")
        )

        // When
        val (id, name, email) = user

        // Then
        assertEquals("user-456", id)
        assertEquals("Maria Santos", name.toString())
        assertEquals("maria@example.com", email.toString())
    }

    @Test
    fun `Users with same data should be equal`() {
        // Given
        val user1 = User(
            id = "user-123",
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com")
        )
        val user2 = User(
            id = "user-123",
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com")
        )

        // When & Then
        assertEquals(user1, user2)
        assertEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    fun `Users with different ids should not be equal`() {
        // Given
        val user1 = User(
            id = "user-123",
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com")
        )
        val user2 = User(
            id = "user-456",
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com")
        )

        // When & Then
        assertNotEquals(user1, user2)
        assertNotEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    fun `Users with different names should not be equal`() {
        // Given
        val user1 = User(
            id = "user-123",
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com")
        )
        val user2 = User(
            id = "user-123",
            name = Name.create("Maria Santos"),
            email = Email.create("joao@example.com")
        )

        // When & Then
        assertNotEquals(user1, user2)
        assertNotEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    fun `Users with different emails should not be equal`() {
        // Given
        val user1 = User(
            id = "user-123",
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com")
        )
        val user2 = User(
            id = "user-123",
            name = Name.create("João Silva"),
            email = Email.create("maria@example.com")
        )

        // When & Then
        assertNotEquals(user1, user2)
        assertNotEquals(user1.hashCode(), user2.hashCode())
    }

    @Test
    fun `User copy should create new instance with updated fields`() {
        // Given
        val originalUser = User(
            id = "user-123",
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com")
        )

        // When
        val updatedUser = originalUser.copy(
            name = Name.create("João Carlos Silva"),
            email = Email.create("joao.carlos@example.com")
        )

        // Then
        assertEquals("user-123", updatedUser.id)
        assertEquals("João Carlos Silva", updatedUser.name.toString())
        assertEquals("joao.carlos@example.com", updatedUser.email.toString())
        
        // Original should remain unchanged
        assertEquals("João Silva", originalUser.name.toString())
        assertEquals("joao@example.com", originalUser.email.toString())
    }

    @Test
    fun `User toString should contain all fields`() {
        // Given
        val user = User(
            id = "user-123",
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com")
        )

        // When
        val toString = user.toString()

        // Then
        assertTrue(toString.contains("user-123"))
        assertTrue(toString.contains("João Silva"))
        assertTrue(toString.contains("joao@example.com"))
    }

    @Test
    fun `User should handle special characters in name and email`() {
        // Given
        val user = User(
            id = "user-456",
            name = Name.create("João-Pedro Silva"),
            email = Email.create("joao.pedro@example.com")
        )

        // When & Then
        assertEquals("user-456", user.id)
        assertEquals("João-Pedro Silva", user.name.toString())
        assertEquals("joao.pedro@example.com", user.email.toString())
    }

    @Test
    fun `User should handle long ids`() {
        // Given
        val longId = "user-" + "a".repeat(100)
        val user = User(
            id = longId,
            name = Name.create("João Silva"),
            email = Email.create("joao@example.com")
        )

        // When & Then
        assertEquals(longId, user.id)
    }

    @Test
    fun `User should handle complex email addresses`() {
        // Given
        val user = User(
            id = "user-789",
            name = Name.create("Maria Santos"),
            email = Email.create("maria.santos+test@example.co.uk")
        )

        // When & Then
        assertEquals("maria.santos+test@example.co.uk", user.email.toString())
    }
} 