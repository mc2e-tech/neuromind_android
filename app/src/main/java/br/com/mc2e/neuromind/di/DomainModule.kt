package br.com.mc2e.neuromind.di

import br.com.mc2e.neuromind.domain.repositories.AuthRepository
import br.com.mc2e.neuromind.domain.repositories.UserRepository
import br.com.mc2e.neuromind.domain.usecases.auth.DisableUserFirstAccessUseCase
import br.com.mc2e.neuromind.domain.usecases.auth.LoginUseCase
import br.com.mc2e.neuromind.domain.usecases.auth.LogoutUseCase
import br.com.mc2e.neuromind.domain.usecases.auth.SilentLoginUseCase

import br.com.mc2e.neuromind.domain.usecases.register.GetUserEmailUseCase
import br.com.mc2e.neuromind.domain.usecases.register.GetUserNameUseCase
import br.com.mc2e.neuromind.domain.usecases.register.RegisterUserUseCase
import br.com.mc2e.neuromind.domain.usecases.register.SaveValidUserEmailUseCase
import br.com.mc2e.neuromind.domain.usecases.register.SaveValidUserNameUseCase
import br.com.mc2e.neuromind.domain.usecases.register.ValidateUserPasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {

    @Provides
    @Singleton
    fun provideLoginUseCase(
        authRepository: AuthRepository
    ): LoginUseCase {
        return LoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideLogoutUseCase(
        authRepository: AuthRepository
    ): LogoutUseCase {
        return LogoutUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSilentLoginUseCase(
        authRepository: AuthRepository
    ): SilentLoginUseCase {
        return SilentLoginUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideRegisterUserUseCase(
        userRepository: UserRepository
    ): RegisterUserUseCase {
        return RegisterUserUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideSaveValidUserEmailUseCase(
        userRepository: UserRepository
    ): SaveValidUserEmailUseCase {
        return SaveValidUserEmailUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserEmailUseCase(
        userRepository: UserRepository
    ): GetUserEmailUseCase {
        return GetUserEmailUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideSaveValidUserNameUseCase(
        userRepository: UserRepository
    ): SaveValidUserNameUseCase {
        return SaveValidUserNameUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserNameUseCase(
        userRepository: UserRepository
    ): GetUserNameUseCase {
        return GetUserNameUseCase(userRepository)
    }

    @Provides
    @Singleton
    fun provideValidateUserPasswordUseCase(): ValidateUserPasswordUseCase {
        return ValidateUserPasswordUseCase()
    }

    @Provides
    @Singleton
    fun provideDisableUserFirstAccessUseCase(
        authRepository: AuthRepository
    ): DisableUserFirstAccessUseCase {
        return DisableUserFirstAccessUseCase(authRepository)
    }
}