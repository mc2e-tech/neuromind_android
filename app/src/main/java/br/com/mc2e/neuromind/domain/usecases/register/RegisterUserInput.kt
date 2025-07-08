package br.com.mc2e.neuromind.domain.usecases.register

import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.valueObjects.Password
import br.com.mc2e.neuromind.domain.valueObjects.Name

data class RegisterUserInput(
    val name: Name,
    val email: Email,
    val password: Password
)