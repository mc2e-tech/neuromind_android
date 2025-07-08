package br.com.mc2e.neuromind.domain.usecases.register

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.ValidationException
import br.com.mc2e.neuromind.domain.failures.ValidationFailure
import br.com.mc2e.neuromind.domain.valueObjects.Password

class ValidateUserPasswordUseCase {
    fun execute(password: String, confirmation: String): Result<Password> {
        return try {
            if (password != confirmation) {
              return Result.Error(ValidationFailure.ConfirmPasswordMismatchError)
            }

            val validatedPassword = Password.create(password)
            Result.Success(validatedPassword)
        } catch (e: ValidationException.PasswordValidationException) {
            Result.Error(ValidationFailure.InvalidPassword(e.message.toString()))
        }
    }
}
