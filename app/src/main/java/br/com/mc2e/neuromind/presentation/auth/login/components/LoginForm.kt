import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.presentation.shared.components.textFields.CustomOutlinedTextFiled
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.smallValue
import br.com.mc2e.neuromind.ui.theme.verticalMediumPadding

@Composable
fun LoginForm(
    email: String,
    password: String,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordFocusRequested: () -> Unit,
    onSubmit: () -> Unit,
    emailErrorMessage: String? = null,
    passwordErrorMessage: String? = null,
) {
    var passwordVisible by remember { mutableStateOf(false) }
    val emailFocusRequester = remember { FocusRequester() }
    val passwordFocusRequester = remember { FocusRequester() }

    val focusManager = LocalFocusManager.current

    val trailingIconId = if (passwordVisible) R.drawable.outline_visibility_icon
    else R.drawable.outline_visibility_off_icon

    val visualTransformation = if (passwordVisible) VisualTransformation.None
    else PasswordVisualTransformation()

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CustomOutlinedTextFiled(
            modifier = Modifier.focusRequester(emailFocusRequester),
            leadingIconId = R.drawable.outline_email_icon,
            placeholder = stringResource(R.string.enter_your_email),
            value = email,
            onValueChange = onEmailChange,
            label = stringResource(R.string.email),
            errorMessage = emailErrorMessage,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    onPasswordFocusRequested()
                    passwordFocusRequester.requestFocus()
                }
            )
        )
        Spacer(modifier = verticalMediumPadding)
        CustomOutlinedTextFiled(
            modifier = Modifier.focusRequester(passwordFocusRequester),
            leadingIconId = R.drawable.outline_lock_icon,
            placeholder = stringResource(R.string.enter_your_password),
            value = password,
            onValueChange = onPasswordChange,
            label = stringResource(R.string.password),
            errorMessage = passwordErrorMessage,
            onTrailingIconClick = { passwordVisible = !passwordVisible },
            trailingIconId = trailingIconId,
            trailingIconDescription = stringResource(R.string.toggle_password_visibility),
            visualTransformation = visualTransformation,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    passwordFocusRequester.freeFocus()
                    focusManager.clearFocus()
                    onSubmit()
                }
            )
        )
    }
}

@PreviewLightDark()
@Composable
fun LoginFormPreview() {
    NeuroMindTheme {
        Box(modifier = Modifier.padding(smallValue)
            .background(MaterialTheme.colorScheme.background)) {
            LoginForm(
                email = "email",
                password = "password",
                onEmailChange = {},
                onPasswordChange = {},
                onSubmit = {},
                onPasswordFocusRequested = {}
            )
        }
    }
}