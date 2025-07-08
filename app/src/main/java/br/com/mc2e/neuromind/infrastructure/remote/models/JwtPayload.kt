package br.com.mc2e.neuromind.infrastructure.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class JwtPayload(
    @Json(name = "sub")
    val userId: String,
    @Json(name = "email")
    val email: String,
    @Json(name = "name")
    val name: String? = null,
    @Json(name = "role")
    val role: String? = null,
    @Json(name = "iat")
    val issuedAt: Long,
    @Json(name = "exp")
    val expiresAt: Long
) 