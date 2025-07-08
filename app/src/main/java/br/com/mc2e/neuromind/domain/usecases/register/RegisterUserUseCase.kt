package br.com.mc2e.neuromind.domain.usecases.register

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.repositories.UserRepository
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(input: RegisterUserInput): Result<Unit> {
        return try {
            userRepository.register(input)
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }
}
