package br.com.mc2e.neuromind.domain.valueObjects

import br.com.mc2e.neuromind.domain.validations.EmailValidator

@ConsistentCopyVisibility
data class Email private constructor(private val value: String) {
    companion object {
        fun create(value: String): Email {
            val normalized = value.trim()
            EmailValidator.validate(normalized)
            return Email(normalized.lowercase())
        }
    }

    fun getValue(): String = value

    override fun toString(): String = value
}