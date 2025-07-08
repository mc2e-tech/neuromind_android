package br.com.mc2e.neuromind.presentation.auth.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.mc2e.neuromind.domain.commons.Result
import br.com.mc2e.neuromind.domain.failures.AuthFailure
import br.com.mc2e.neuromind.domain.failures.Failure
import br.com.mc2e.neuromind.domain.usecases.auth.SilentLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val silentLoginUseCase: SilentLoginUseCase
) : ViewModel() {

    private val _uiEvent = MutableSharedFlow<SplashUiEvent>()
    val uiEvent: SharedFlow<SplashUiEvent> = _uiEvent.asSharedFlow()

    init {
        viewModelScope.launch {
            when (val result = silentLoginUseCase.invoke()) {
                is Result.Error -> handleSilentLoginErrors(result.failure)
                is Result.Success -> _uiEvent.emit(SplashUiEvent.NavigateToHome)
            }
        }
    }

    private fun handleSilentLoginErrors(failure: Failure) {
        viewModelScope.launch {
            when (failure) {
                is AuthFailure.UserFirstAccess -> _uiEvent.emit(SplashUiEvent.NavigateToIntro)
                is AuthFailure.RegisterStarted -> {
                    _uiEvent.emit(
                        SplashUiEvent.NavigateToRegister(
                            name = failure.name,
                            email = failure.email
                        ),
                    )
                }

                else -> _uiEvent.emit(SplashUiEvent.NavigateToLogin)
            }
        }
    }
}