package br.com.mc2e.neuromind.domain.usecases.auth

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            authRepository.logout()
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }
} 