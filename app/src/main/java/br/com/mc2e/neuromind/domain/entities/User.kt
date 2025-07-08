package br.com.mc2e.neuromind.domain.entities

import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.valueObjects.Name

data class User(
    val id: String,
    val name: Name,
    val email: Email
) 