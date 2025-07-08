package br.com.mc2e.neuromind.domain.commons

import br.com.mc2e.neuromind.domain.failures.Failure

sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val failure: Failure) : Result<Nothing>()

    val isSuccess: Boolean
        get() = this is Success

    val isError: Boolean
        get() = this is Error

    fun getOrNull(): T? = when (this) {
        is Success -> data
        is Error -> null
    }

    fun failureOrNull(): Failure? = when (this) {
        is Success -> null
        is Error -> failure
    }
} 