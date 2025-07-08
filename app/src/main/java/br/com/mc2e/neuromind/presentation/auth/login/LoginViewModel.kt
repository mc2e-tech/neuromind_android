package br.com.mc2e.neuromind.presentation.auth.login

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mc2e.neuromind.domain.usecases.auth.LoginUseCase
import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.failures.ValidationFailure
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel() {

    // StateFlow to store the UI state
    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Form())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    // SharedFlow to emit events to the UI
    private val _uiEvent = MutableSharedFlow<LoginUiEvent>()
    val uiEvent: SharedFlow<LoginUiEvent> = _uiEvent.asSharedFlow()

    private var email: String = ""
    private var password: String = ""

    fun onEvent(event: LoginUserInputEvent) {
        viewModelScope.launch {
            handleEventSuspend(event)
        }
    }

    private suspend fun handleEventSuspend(event: LoginUserInputEvent) {
        when (event) {
            is LoginUserInputEvent.EmailChanged -> {
                email = event.email
                if (email.isEmpty()) _uiEvent.emit(LoginUiEvent.ResetEmailError)
                _uiState.value =  LoginUiState.Form(
                    email = email,
                    password = password
                )
            }

            is LoginUserInputEvent.PasswordChanged -> {
                password = event.password
                if (password.isEmpty()) _uiEvent.emit(LoginUiEvent.ResetPasswordError)
                _uiState.value = LoginUiState.Form(
                    email = email,
                    password = password
                )
            }

            is LoginUserInputEvent.Submit -> login()

            is LoginUserInputEvent.ForgotPasswordClicked -> _uiEvent.emit(
                LoginUiEvent.NavigateToForgotPassword
            )

            is LoginUserInputEvent.RegisterClicked -> _uiEvent.emit(
                LoginUiEvent.NavigateToRegister
            )

            is LoginUserInputEvent.PasswordFieldFocusRequested -> {
                if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
                    _uiEvent.emit(LoginUiEvent.EmailFieldError)
                }
            }
        }
    }


    private fun login() {
        viewModelScope.launch {
            if (email.isEmpty() && password.isEmpty()) {
                _uiEvent.emit(LoginUiEvent.EmailFieldError)
                _uiEvent.emit(LoginUiEvent.PasswordFieldError)
                return@launch
            }

            if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
                _uiEvent.emit(LoginUiEvent.EmailFieldError)
                return@launch
            }

            if (password.isBlank()) {
                _uiEvent.emit(LoginUiEvent.PasswordFieldError)
                return@launch
            }


            _uiState.value = when (uiState.value) {
                is LoginUiState.Form -> (_uiState.value as LoginUiState.Form)
                    .copy(isLoading = true)

                else -> LoginUiState.Form(
                    email = email,
                    password = password,
                    isLoading = true
                )
            }

            when (val result = loginUseCase.invoke(email, password)) {
                is Result.Error -> {
                    handleLoginErrors(result.failure)
                    _uiState.value = LoginUiState.Form(
                        email = email,
                        password = password,
                        isLoading = false
                    )
                }

                is Result.Success -> {
                    _uiState.value = LoginUiState.Success //todo: testar pra ver se há necessidade
                    _uiEvent.emit(LoginUiEvent.NavigateToHome)
                }
            }
        }
    }

    private fun handleLoginErrors(failure: Failure) {
       viewModelScope.launch {
           when(failure) {
               is ValidationFailure.InvalidEmail -> _uiEvent.emit(LoginUiEvent.EmailFieldError)
               is ValidationFailure.InvalidPassword ->
                   _uiEvent.emit(LoginUiEvent.PasswordFieldError)
               else -> _uiEvent.emit(LoginUiEvent.ShowGlobalError)
           }
       }
    }
} 