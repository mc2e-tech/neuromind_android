package br.com.mc2e.neuromind.di

import br.com.mc2e.neuromind.data.datasources.remote.apis.AuthApi
import br.com.mc2e.neuromind.infrastructure.local.storage.SecureStorage
import br.com.mc2e.neuromind.infrastructure.local.storage.SecureStorageImpl
import br.com.mc2e.neuromind.infrastructure.local.storage.UserPreferencesStorage
import br.com.mc2e.neuromind.infrastructure.local.storage.UserPreferencesStorageImpl
import br.com.mc2e.neuromind.infrastructure.remote.interceptors.JwtInterceptor
import br.com.mc2e.neuromind.infrastructure.remote.interceptors.TokenRefreshInterceptor
import br.com.mc2e.neuromind.infrastructure.remote.services.JwtDecoder
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class InfrastructureModule {

    @Binds
    @Singleton
    abstract fun bindSecureStorage(
        secureStorageImpl: SecureStorageImpl
    ): SecureStorage

    @Binds
    @Singleton
    abstract fun bindUserPreferencesStorage(
        userPreferencesStorage: UserPreferencesStorageImpl
    ): UserPreferencesStorage

    companion object {
        //todo: passar para o env
        private const val BASE_URL = "http://192.168.3.116:3000/"

        @Provides
        @Singleton
        fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Provides
        @Singleton
        fun provideJwtDecoder(moshi: Moshi): JwtDecoder {
            return JwtDecoder(moshi)
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            jwtInterceptor: JwtInterceptor,
            tokenRefreshInterceptor: TokenRefreshInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(jwtInterceptor)
                .addInterceptor(tokenRefreshInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideMoshi(): Moshi {
            return Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build()
        }

        @Provides
        @Singleton
        fun provideAuthApiService(retrofit: Retrofit): AuthApi {
            return retrofit.create(AuthApi::class.java)
        }
    }
} 