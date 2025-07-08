package br.com.mc2e.neuromind.di

import br.com.mc2e.neuromind.data.datasources.local.AuthLocalSecureDataSource
import br.com.mc2e.neuromind.data.datasources.local.AuthLocalSecureDataSourceImpl
import br.com.mc2e.neuromind.data.datasources.local.UserLocalDataSource
import br.com.mc2e.neuromind.data.datasources.local.UserLocalDataSourceImpl
import br.com.mc2e.neuromind.data.datasources.remote.AuthRemoteDataSource
import br.com.mc2e.neuromind.data.datasources.remote.AuthRemoteDataSourceImpl
import br.com.mc2e.neuromind.data.repositories.AuthRepositoryImpl
import br.com.mc2e.neuromind.data.repositories.UserRepositoryImpl
import br.com.mc2e.neuromind.domain.repositories.AuthRepository
import br.com.mc2e.neuromind.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindAuthLocalSecureDataSource(
        authLocalSecureDataSourceImpl: AuthLocalSecureDataSourceImpl
    ): AuthLocalSecureDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindUserLocalDataSource(
        userLocalDataSourceImpl: UserLocalDataSourceImpl
    ): UserLocalDataSource
} 