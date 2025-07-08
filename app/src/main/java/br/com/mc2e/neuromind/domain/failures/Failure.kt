package br.com.mc2e.neuromind.domain.failures

sealed class Failure {
    data object ServerError : Failure()
    data object LocalError : Failure()
    data object Unauthorized : Failure()
    data object NotFound : Failure()
    
    data class Unexpected(val exception: Exception) : Failure()
} 