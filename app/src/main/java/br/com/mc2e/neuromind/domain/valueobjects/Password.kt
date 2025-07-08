package br.com.mc2e.neuromind.domain.valueObjects

import br.com.mc2e.neuromind.domain.validations.PasswordValidator

@ConsistentCopyVisibility
data class Password private constructor(private val value: String) {
    companion object {
        fun create(value: String): Password {
            val trimmed = value.trim()
            PasswordValidator.validate(trimmed)
            return Password(trimmed)
        }
    }

    fun getValue(): String = value

    override fun toString(): String = "[REDACTED]"
} 