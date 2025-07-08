package br.com.mc2e.neuromind.domain.usecases.auth

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.AuthFailure
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.repositories.AuthRepository
import javax.inject.Inject

class SilentLoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit> {
        return try {
            val refreshTokenResult = authRepository.getRefreshToken()
            if (refreshTokenResult is Result.Error) {
                // If unable to get the refresh token, try to fetch pre register info
                return tryGetPreRegisterInfo()
            }
            
            val refreshToken = (refreshTokenResult as Result.Success).data
            
            val refreshSessionResult = authRepository.refreshSession(refreshToken)
            if (refreshSessionResult is Result.Error) {
                return Result.Error(Failure.Unauthorized)
            }
            
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }

    private suspend fun tryGetPreRegisterInfo(): Result<Unit> {
        return try {
            val preRegisterInfoResult = authRepository.getPreRegisterInfo()
            if (preRegisterInfoResult is Result.Error) {
                return preRegisterInfoResult
            }
            
            val preRegisterInfo = (preRegisterInfoResult as Result.Success).data
            if (preRegisterInfo.isFirstAccess) {
                return Result.Error(AuthFailure.UserFirstAccess)
            }
            // Checks if the user has already started registration (has name or email) but is not on first access
            if (preRegisterInfo.name != null || preRegisterInfo.email != null) {
                return Result.Error(
                    AuthFailure.RegisterStarted(preRegisterInfo.name, preRegisterInfo.email)
                )
            }

            return Result.Error(Failure.Unauthorized)
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }
} 