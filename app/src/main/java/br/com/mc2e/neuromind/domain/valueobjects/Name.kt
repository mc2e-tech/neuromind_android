package br.com.mc2e.neuromind.domain.valueObjects

import br.com.mc2e.neuromind.domain.validations.NameValidator

@ConsistentCopyVisibility
data class Name private constructor(private val value: String) {
    companion object {
        fun create(value: String): Name {
            val normalized = value.trim()
            NameValidator.validate(normalized)
            return Name(normalized)
        }
    }

    fun getValue(): String = value

    override fun toString(): String = value
}
