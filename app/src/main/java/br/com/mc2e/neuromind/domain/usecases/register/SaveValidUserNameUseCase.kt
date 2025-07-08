package br.com.mc2e.neuromind.domain.usecases.register

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.valueObjects.Name
import br.com.mc2e.neuromind.domain.failures.ValidationException
import br.com.mc2e.neuromind.domain.failures.ValidationFailure
import br.com.mc2e.neuromind.domain.repositories.UserRepository
import javax.inject.Inject

class SaveValidUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    //TODO Descomentar
    suspend fun execute(name: String): Result<Name> {
        return try {
//            val validatedName = Name.create(name)
//            userRepository.saveUserName(validatedName.getValue())
//            Result.Success(validatedName)

            Result.Success(Name.create(name))

//            Result.Error(ValidationFailure.InvalidCompleteName)
        } catch (_: ValidationException.CompleteNameValidationException) {
            Result.Error(ValidationFailure.InvalidCompleteName)
        } catch (_: ValidationException.NameValidationException) {
            Result.Error(ValidationFailure.InvalidName)
        }
    }
}