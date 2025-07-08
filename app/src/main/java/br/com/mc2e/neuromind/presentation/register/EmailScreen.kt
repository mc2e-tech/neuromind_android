package br.com.mc2e.neuromind.presentation.register

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.presentation.register.components.RegisterHeader
import br.com.mc2e.neuromind.presentation.shared.components.buttons.ActionText
import br.com.mc2e.neuromind.presentation.shared.components.buttons.PrimaryButton
import br.com.mc2e.neuromind.presentation.shared.components.textFields.CustomOutlinedTextFiled
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.horizontalPaddingValue
import br.com.mc2e.neuromind.ui.theme.verticalLargePadding
import br.com.mc2e.neuromind.ui.theme.verticalXXXXLargePadding

@Composable
fun EmailScreen(
    modifier: Modifier = Modifier,
    uiState: RegisterUiState.EmailStep,
    onEvent: (RegisterUserInputEvent) -> Unit,
) {
    val nameFocusRequester = remember { FocusRequester() }

    val navigationBarPadding =
        WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()


    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        RegisterHeader(
            iconId = R.drawable.outline_user_email_icon,
            iconDescription = stringResource(R.string.email_description),
            modifier = Modifier.size(44.dp),
            title = stringResource(R.string.onboarding_start),
            description = stringResource(R.string.email_prompt_description),
            targetProgress = 3f / 5f
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = horizontalPaddingValue),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = verticalXXXXLargePadding)
            CustomOutlinedTextFiled(
                modifier = Modifier.focusRequester(nameFocusRequester),
                leadingIconId = R.drawable.outline_email_icon,
                placeholder = stringResource(R.string.enter_your_email_placeholder),
                value = uiState.email,
                onValueChange = { onEvent(RegisterUserInputEvent.NameChanged(it)) },
                label = stringResource(R.string.email),
                errorMessage = uiState.error?.let { stringResource(uiState.error)  },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        nameFocusRequester.freeFocus()
                        onEvent(RegisterUserInputEvent.NextStepClicked)
                    }
                )
            )
            Spacer(modifier = verticalLargePadding)
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(R.string.to_continue),
                enabled =
                    uiState.email.isNotEmpty(),
                loading = false,
                onClick = { onEvent(RegisterUserInputEvent.NextStepClicked) },
            )
            Spacer(modifier = Modifier.weight(1f))
            ActionText(
                onTapping = { onEvent(RegisterUserInputEvent.OnBack) },
                message = null,
                actionText = stringResource(R.string.to_back)
            )
            Spacer(modifier = Modifier.height(navigationBarPadding))
        }

    }
}


@PreviewLightDark()
@Composable
private fun PreviewEmailScreen() {
    NeuroMindTheme {
        EmailScreen(
            uiState = RegisterUiState.EmailStep(),
            onEvent = {},
        )
    }
}