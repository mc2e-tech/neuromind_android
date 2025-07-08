package br.com.mc2e.neuromind.domain.usecases.register

import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.repositories.UserRepository
import javax.inject.Inject

open class GetUserNameUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    open suspend fun execute(): Result<String?> {
        return try {
            var result = userRepository.getUserName()
            Result.Success(result)
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }
}