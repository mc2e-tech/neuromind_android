package br.com.mc2e.neuromind.infrastructure.remote.services

import br.com.mc2e.neuromind.infrastructure.remote.models.JwtPayload
import com.auth0.jwt.JWT
import com.auth0.jwt.exceptions.JWTDecodeException
import com.squareup.moshi.Moshi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class JwtDecoder @Inject constructor(
    private val moshi: Moshi
) {
    
    fun decodeToken(token: String): JwtPayload? {
        return try {
            val jwt = JWT.decode(token)
            val payloadBytes = android.util.Base64.decode(
                jwt.payload,
                android.util.Base64.URL_SAFE or android.util.Base64.NO_PADDING or android.util.Base64.NO_WRAP
            )
            val payloadString = String(payloadBytes)
            
            moshi.adapter(JwtPayload::class.java).fromJson(payloadString)
        } catch (e: JWTDecodeException) {
            null
        } catch (e: Exception) {
            null
        }
    }
    
    fun isTokenExpired(token: String): Boolean {
        val payload = decodeToken(token) ?: return true
        val currentTime = System.currentTimeMillis() / 1000 // Convert to seconds
        return payload.expiresAt <= currentTime
    }
    
    fun getTokenExpirationTime(token: String): Long? {
        return decodeToken(token)?.expiresAt
    }
} 