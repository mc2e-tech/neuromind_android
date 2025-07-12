package br.com.mc2e.neuromind.presentation.auth.intro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mc2e.neuromind.domain.usecases.auth.DisableUserFirstAccessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class IntroViewModel @Inject constructor(
    private val disableUserFirstAccessUseCase: DisableUserFirstAccessUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<IntroUiEvent>()
    val uiEvent: SharedFlow<IntroUiEvent> = _uiEvent.asSharedFlow()

    fun onEvent(event: IntroUserInputEvent) {
        viewModelScope.launch {
            handleEventSuspend(event)
        }
    }

    private suspend fun handleEventSuspend(event: IntroUserInputEvent) {
        when (event) {
            IntroUserInputEvent.RegisterClicked -> {
                disableUserFirstAccessUseCase.invoke()
                _uiEvent.emit(IntroUiEvent.NavigateToRegister)
            }

            IntroUserInputEvent.LoginClicked -> {
                disableUserFirstAccessUseCase.invoke()
                _uiEvent.emit(IntroUiEvent.NavigateToLogin)
            }
        }
    }
}