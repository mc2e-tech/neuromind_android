package br.com.mc2e.neuromind.presentation.shared.components.textFields

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.smallValue

@Composable
fun CustomOutlinedTextFiled(
    modifier: Modifier = Modifier,
    leadingIconId: Int,
    label: String,
    placeholder: String,
    value: String,
    keyboardActions: KeyboardActions = KeyboardActions(),
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    trailingIconId: Int? = null,
    trailingIconDescription: String? = null,
    visualTransformation: VisualTransformation? = null,
    errorMessage: String? = null,
    onValueChange: (String) -> Unit = {},
    onTrailingIconClick: () -> Unit = {},
) {
    val hasError = !errorMessage.isNullOrBlank()

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                placeholder,
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        },
        label = {
            Text(
                label,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        leadingIcon = {
            Icon(
                painterResource(leadingIconId),
                contentDescription = placeholder,
            )
        },
        trailingIcon = if (trailingIconId != null && !trailingIconDescription.isNullOrBlank()) {
            {
                IconButton(onClick = onTrailingIconClick)  {
                    Icon(
                        painterResource(trailingIconId),
                        contentDescription = trailingIconDescription,
                    )
                }
            }
        } else null,
        supportingText = if (hasError) {
            {
                Text(
                    errorMessage!!,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.onError
                    )
                )
            }
        } else null,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        singleLine = true,
        isError = hasError,
        visualTransformation = visualTransformation ?: VisualTransformation.None,
        keyboardActions = keyboardActions,
        keyboardOptions = keyboardOptions,
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
            unfocusedLabelColor = MaterialTheme.colorScheme.inversePrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.inversePrimary,
            errorTextColor = MaterialTheme.colorScheme.onError,
            errorBorderColor = MaterialTheme.colorScheme.onError,
            errorLeadingIconColor = MaterialTheme.colorScheme.onError,
            errorLabelColor = MaterialTheme.colorScheme.onError,
            errorTrailingIconColor = MaterialTheme.colorScheme.onError
        )
    )
}

@PreviewLightDark()
@Composable
private fun CustomOutlinedTextFiledPreview() {
    NeuroMindTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            CustomOutlinedTextFiled(
                leadingIconId = R.drawable.outline_lock_icon,
                trailingIconId = R.drawable.outline_visibility_icon,
                trailingIconDescription = stringResource(R.string.toggle_password_visibility),
                placeholder = stringResource(R.string.enter_your_password),
                onValueChange = {},
                value = "",
                label = stringResource(R.string.password),
            )
            Spacer(modifier = Modifier.height(smallValue))
            CustomOutlinedTextFiled(
                leadingIconId = R.drawable.outline_email_icon,
                placeholder = stringResource(R.string.enter_your_email),
                onValueChange = {},
                value = "marcosp.sousa@gmail.com",
                label = stringResource(R.string.email),
            )
            Spacer(modifier = Modifier.height(smallValue))
            CustomOutlinedTextFiled(
                leadingIconId = R.drawable.outline_email_icon,
                placeholder = stringResource(R.string.enter_your_email),
                onValueChange = {},
                value = "email_invalido",
                label = stringResource(R.string.email),
                errorMessage = stringResource(R.string.invalid_email),
            )
        }
    }
}