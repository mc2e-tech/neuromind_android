package br.com.mc2e.neuromind.domain.usecases.auth

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.failures.ValidationException
import br.com.mc2e.neuromind.domain.failures.ValidationFailure
import br.com.mc2e.neuromind.domain.repositories.AuthRepository
import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.valueObjects.Password
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<Unit> {
        return try {
            val emailValue = Email.create(email)
            val passwordValue = Password.create(password)
            authRepository.login(emailValue, passwordValue)
        } catch (e: ValidationException.EmailValidationException) {
            Result.Error(ValidationFailure.InvalidEmail(e.message ?: "Invalid email"))
        } catch (e: ValidationException.PasswordValidationException) {
            Result.Error(ValidationFailure.InvalidPassword(e.message ?: "Invalid password"))
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }
}