package br.com.mc2e.neuromind.data.datasources.remote.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LoginRequest(
    val email: String,
    val password: String
) 