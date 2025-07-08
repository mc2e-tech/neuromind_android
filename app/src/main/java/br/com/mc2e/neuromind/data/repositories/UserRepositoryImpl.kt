package br.com.mc2e.neuromind.data.repositories

import br.com.mc2e.neuromind.data.datasources.local.UserLocalDataSource
import br.com.mc2e.neuromind.data.datasources.remote.AuthRemoteDataSource
import br.com.mc2e.neuromind.data.datasources.remote.models.RegisterRequest
import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.repositories.UserRepository
import br.com.mc2e.neuromind.domain.usecases.register.RegisterUserInput
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: UserLocalDataSource
) : UserRepository {

    override suspend fun register(input: RegisterUserInput): Result<Unit> {
        return try {
            val request = RegisterRequest(
                input.name.getValue(),
                input.email.getValue(),
                input.password.getValue()
            )

            remoteDataSource.register(request)

            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }

    override suspend fun saveUserName(name: String) {
        localDataSource.saveUserName(name)
    }

    override suspend fun getUserName(): String? {
        return localDataSource.getUserName()
    }

    override suspend fun saveUserEmail(email: String) {
        localDataSource.saveUserEmail(email)
    }

    override suspend fun getUserEmail(): String? {
        return localDataSource.getUserEmail()
    }
}