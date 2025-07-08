package br.com.mc2e.neuromind.presentation.register

sealed class RegisterUiState {
    data class NameStep(
        val name: String = "",
        val error: Int? = null,
    ) : RegisterUiState()

    data class EmailStep(
        val email: String = "",
        val error: Int? = null,
    ) : RegisterUiState()

    data class PasswordStep(
        val password: String = "",
        val confirmPassword: String = "",
        val error: Int? = null,
        val isLoading: Boolean = false
    ) : RegisterUiState()

    object Success : RegisterUiState()
    object Error : RegisterUiState()
}