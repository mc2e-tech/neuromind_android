package br.com.mc2e.neuromind.domain.entities

data class PreRegisterInfo(
    val name: String? = null,
    val email: String? = null,
    val isFirstAccess: Boolean = true
) 