package br.com.mc2e.neuromind.domain.validations

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenValidator @Inject constructor() {
    fun isExpired(token: String?): Boolean {
        if (token.isNullOrBlank()) {
            return true
        }
        
        // TODO: Implement JWT token expiration validation
        // For now, we'll delegate to infrastructure layer
        return false
    }
} 