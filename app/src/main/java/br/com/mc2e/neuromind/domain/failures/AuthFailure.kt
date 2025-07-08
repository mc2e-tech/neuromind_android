package br.com.mc2e.neuromind.domain.failures

sealed class AuthFailure : Failure() {
    data object InvalidCredentials : AuthFailure()
    data object TokenExpired : AuthFailure()
    data object UserFirstAccess : AuthFailure()
    data class RegisterStarted(val name: String?, val email: String?) : AuthFailure()
} 