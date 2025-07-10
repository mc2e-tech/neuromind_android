package br.com.mc2e.neuromind.domain.usecases.register

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.failures.ValidationException
import br.com.mc2e.neuromind.domain.failures.ValidationFailure
import br.com.mc2e.neuromind.domain.repositories.UserRepository
import javax.inject.Inject

open class SaveValidUserEmailUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    open suspend fun execute(email: String): Result<Email> {
        return try {
//            val validatedEmail = Email.create(email)
//            userRepository.saveUserEmail(validatedEmail.getValue())
//            Result.Success(validatedEmail)


            Result.Success(Email.create(email))
        } catch (e: ValidationException.EmailValidationException) {
            Result.Error(ValidationFailure.InvalidEmail(e.message.toString()))
        }
    }
}