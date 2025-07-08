import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.presentation.auth.login.LoginUiEvent
import br.com.mc2e.neuromind.presentation.auth.login.LoginUiState
import br.com.mc2e.neuromind.presentation.auth.login.LoginUserInputEvent
import br.com.mc2e.neuromind.presentation.auth.login.components.ForgotPasswordButton
import br.com.mc2e.neuromind.presentation.shared.components.buttons.ActionText
import br.com.mc2e.neuromind.presentation.shared.components.buttons.PrimaryButton
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.horizontalPaddingValue
import br.com.mc2e.neuromind.ui.theme.verticalMediumPadding
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    uiState: LoginUiState.Form,
    uiEventFlow: Flow<LoginUiEvent>,
    onEvent: (LoginUserInputEvent) -> Unit,
) {
    val context = LocalContext.current
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val navigationBarPadding =
        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()

    LaunchedEffect(Unit) {
        uiEventFlow.collect { event ->
            when (event) {
                is LoginUiEvent.EmailFieldError ->
                    emailError = context.getString(R.string.invalid_email)

                is LoginUiEvent.PasswordFieldError ->
                    passwordError = context.getString(R.string.invalid_password)

                is LoginUiEvent.ShowGlobalError -> {
                    emailError = context.getString(R.string.email_or_passsword_invalids)
                    passwordError = context.getString(R.string.email_or_passsword_invalids)
                }

                is LoginUiEvent.ResetEmailError -> emailError = null
                is LoginUiEvent.ResetPasswordError -> passwordError = null

                else -> {}
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LoginHeader()
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPaddingValue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = verticalMediumPadding)
            LoginForm(
                onEmailChange = { onEvent(LoginUserInputEvent.EmailChanged(it)) },
                onPasswordChange = { onEvent(LoginUserInputEvent.PasswordChanged(it)) },
                password = uiState.password,
                email = uiState.email,
                passwordErrorMessage = passwordError,
                emailErrorMessage = emailError,
                onSubmit = { onEvent(LoginUserInputEvent.Submit) },
                onPasswordFocusRequested = {
                    onEvent(LoginUserInputEvent.PasswordFieldFocusRequested)
                }
            )
            Spacer(modifier = verticalMediumPadding)
            ForgotPasswordButton(
                onForgotPasswordClicked = { onEvent(LoginUserInputEvent.ForgotPasswordClicked) }
            )
            Spacer(modifier = verticalMediumPadding)
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.login),
                enabled = !uiState.isLoading &&
                        uiState.email.isNotEmpty() && uiState.password.isNotEmpty(),
                loading = uiState.isLoading,
                onClick = { onEvent(LoginUserInputEvent.Submit) },
            )
            Spacer(modifier = Modifier.weight(1f))
            ActionText(
                onTapping = { onEvent(LoginUserInputEvent.RegisterClicked) },
                message = stringResource(R.string.do_not_have_account_yet),
                actionText = stringResource(R.string.register)
            )
            Spacer(modifier = Modifier.height(navigationBarPadding))
        }
    }
}



@PreviewLightDark()
@Composable
fun LoginScreenPreview() {
    NeuroMindTheme {
        LoginScreen(
            uiState = LoginUiState.Form(),
            onEvent = {},
            uiEventFlow = flowOf()
        )
    }
}