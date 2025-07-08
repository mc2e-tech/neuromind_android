package br.com.mc2e.neuromind.data.repositories

import br.com.mc2e.neuromind.data.datasources.local.AuthLocalSecureDataSource
import br.com.mc2e.neuromind.data.datasources.local.UserLocalDataSource
import br.com.mc2e.neuromind.data.datasources.remote.AuthRemoteDataSource
import br.com.mc2e.neuromind.data.datasources.remote.models.LoginRequest
import br.com.mc2e.neuromind.data.datasources.remote.models.RefreshTokenRequest
import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.entities.PreRegisterInfo
import br.com.mc2e.neuromind.domain.entities.User
import br.com.mc2e.neuromind.domain.failures.AuthFailure
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.repositories.AuthRepository
import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.valueObjects.Name
import br.com.mc2e.neuromind.domain.valueObjects.Password
import br.com.mc2e.neuromind.infrastructure.remote.services.JwtDecoder
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

class AuthRepositoryImpl @Inject constructor(
    private val authRemoteDataSource: AuthRemoteDataSource,
    private val authLocalSecureDataSource: AuthLocalSecureDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val jwtDecoder: JwtDecoder
) : AuthRepository {
    override suspend fun login(email: Email, password: Password): Result<Unit> {
        return try {
            val request = LoginRequest(email = email.getValue(), password = password.getValue())
            val loginResponse = authRemoteDataSource.login(request)
            
            authLocalSecureDataSource.saveAccessToken(loginResponse.accessToken)
            authLocalSecureDataSource.saveRefreshToken(loginResponse.refreshToken)
            
            Result.Success(Unit)
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> Result.Error(AuthFailure.InvalidCredentials)
                else -> Result.Error(Failure.ServerError)
            }
        } catch (e: IOException) {
            Result.Error(Failure.LocalError)
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            authLocalSecureDataSource.clearTokens()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }

    override suspend fun getCurrentUser(): Result<User> {
        return try {
            val token = authLocalSecureDataSource.getAccessToken() 
                ?: return Result.Error(Failure.Unauthorized)
            
            val payload = jwtDecoder.decodeToken(token) 
                ?: return Result.Error(Failure.Unauthorized)
            
            val email = Email.create(payload.email)
            val name = Name.create(payload.name ?: "")
            val user = User(
                id = payload.userId,
                name = name,

                email = email
            )
            Result.Success(user)
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }

    override suspend fun getRefreshToken(): Result<String> {
        return try {
            val refreshToken = authLocalSecureDataSource.getRefreshToken()
            if (refreshToken != null) {
                Result.Success(refreshToken)
            } else {
                Result.Error(AuthFailure.InvalidCredentials)
            }
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }

    override suspend fun refreshSession(refreshToken: String): Result<Unit> {
        return try {
            val request = RefreshTokenRequest(refreshToken)
            val loginResponse = authRemoteDataSource.refreshToken(request)
            
            authLocalSecureDataSource.saveAccessToken(loginResponse.accessToken)
            authLocalSecureDataSource.saveRefreshToken(loginResponse.refreshToken)
            
            Result.Success(Unit)
        } catch (e: HttpException) {
            when (e.code()) {
                401 -> Result.Error(AuthFailure.InvalidCredentials)
                else -> Result.Error(Failure.ServerError)
            }
        } catch (e: IOException) {
            Result.Error(Failure.LocalError)
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }

    override suspend fun getPreRegisterInfo(): Result<PreRegisterInfo> {
        return try {
            val name = userLocalDataSource.getUserName()
            val email = userLocalDataSource.getUserEmail()
            val isFirstAccess = userLocalDataSource.getIsFirstAccess()
            
            val preRegisterInfo = PreRegisterInfo(
                name = name,
                email = email,
                isFirstAccess = isFirstAccess
            )
            
            Result.Success(preRegisterInfo)
        } catch (e: Exception) {
            Result.Error(Failure.Unexpected(e))
        }
    }
} 