package br.com.mc2e.neuromind.presentation.shared.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.bodyMediumLight
import br.com.mc2e.neuromind.ui.theme.smallValue
import br.com.mc2e.neuromind.ui.theme.xxSmallValue

@Composable
fun ActionText(
    onTapping: () -> Unit,
    message: String,
    actionText: String,

) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            style = bodyMediumLight.copy(color = MaterialTheme.colorScheme.onBackground)
        )
        TextButton(
            contentPadding = PaddingValues(start = xxSmallValue, end = smallValue),
            onClick = onTapping,
        ) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Start
                ),
            )
        }
    }
}

@PreviewLightDark()
@Composable
fun RegisterActionTextPreview() {
    NeuroMindTheme {
        Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ActionText(
                onTapping = {},
                message = stringResource(R.string.do_not_have_account_yet),
                actionText = stringResource(R.string.register)
            )
        }
    }
}