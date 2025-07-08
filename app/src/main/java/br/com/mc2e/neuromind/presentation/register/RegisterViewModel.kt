package br.com.mc2e.neuromind.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.domain.usecases.register.SaveValidUserNameUseCase
import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.ValidationFailure
import br.com.mc2e.neuromind.domain.valueObjects.Email
import br.com.mc2e.neuromind.domain.valueObjects.Name
import br.com.mc2e.neuromind.domain.valueObjects.Password
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
class RegisterViewModel @Inject constructor(
    private val saveValidUserNameUseCase: SaveValidUserNameUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<RegisterUiState>(RegisterUiState.NameStep())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<RegisterUiEvent>()
    val uiEvent: SharedFlow<RegisterUiEvent> = _uiEvent.asSharedFlow()

    var name: Name? = null
    var email: Email? = null
    var password: Password? = null

    fun preload(
        name: String? = null,
        email: String? = null
    ) {

        when {
            !email.isNullOrBlank() && !name.isNullOrBlank() -> {
                validateEmailArg(name, email)
            }

            !name.isNullOrBlank() -> {
                validateNameArg(name)
            }

            else -> {
                _uiState.value = RegisterUiState.NameStep()
            }
        }

    }

    fun onEvent(event: RegisterUserInputEvent) {
        viewModelScope.launch {
            when (val state = _uiState.value) {
                is RegisterUiState.NameStep -> handleNameStep(event, state)
                is RegisterUiState.EmailStep -> handleEmailStep(event, state)
                //TODO: Continuar esse método
                else -> {}
            }
        }
    }

    private suspend fun handleNameStep(
        event: RegisterUserInputEvent,
        state: RegisterUiState.NameStep
    ) {
        when (event) {
            is RegisterUserInputEvent.NameChanged -> {
                _uiState.value = state.copy(name = event.value, error = null)
            }

            is RegisterUserInputEvent.NextStepClicked ->
                validateName(state.name, state)

            is RegisterUserInputEvent.LoginClicked -> _uiEvent.emit(RegisterUiEvent.NavigateToLogin)
            else -> {}
        }
    }


    private fun validateName(nameValue: String, state: RegisterUiState.NameStep) {
        viewModelScope.launch {

            when (val result = saveValidUserNameUseCase.execute(nameValue)) {
                is Result.Success -> {
                    name = result.data
                    _uiState.value = RegisterUiState.EmailStep()
                }

                is Result.Error -> {
                    when (result.failure) {
                        is ValidationFailure.InvalidCompleteName -> {
                            _uiState.value = state.copy(error = R.string.incomplete_name)
                        }

                        else -> {
                            _uiState.value = state.copy(error = R.string.invalid_name)
                        }
                    }

                }
            }

        }
    }

    private fun validateNameArg(nameArg: String) {
        try {
            val nameValid = Name.create(nameArg)
            name = nameValid
            _uiState.value = RegisterUiState.EmailStep()
        } catch (_: Exception) {
            _uiState.value = RegisterUiState.NameStep()
        }
    }

    private fun handleEmailStep(
        event: RegisterUserInputEvent,
        step: RegisterUiState.EmailStep
    ) {

        when (event) {
            is RegisterUserInputEvent.EmailChanged -> {
                _uiState.value = step.copy(email = event.value, error = null)
            }
            is RegisterUserInputEvent.OnBack -> {
                _uiState.value = RegisterUiState.NameStep(name = name?.getValue() ?: "")
            }
            //TODO: Continuar esse método
            else -> {}
        }
    }

    private fun validateEmailArg(nameArg: String, emailArg: String) {
        try {
            validateNameArg(nameArg)
            val emailValid = Email.create(emailArg)
            email = emailValid
            _uiState.value = RegisterUiState.PasswordStep()
        } catch (_: Exception) {
            _uiState.value = RegisterUiState.NameStep()
        }
    }


}