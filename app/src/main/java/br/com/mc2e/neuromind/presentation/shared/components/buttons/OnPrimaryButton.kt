package br.com.mc2e.neuromind.presentation.shared.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.Purple400
import br.com.mc2e.neuromind.ui.theme.smallValue
import br.com.mc2e.neuromind.ui.theme.verticalSmallPadding

@Composable
fun OnPrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    PrimaryButton(
        text = text,
        loading = loading,
        modifier = modifier,
        onClick = onClick,
        enabled = enabled && !loading,
        colors =  ButtonDefaults.buttonColors(
            containerColor =  Color.White,
            contentColor = Purple400,
            disabledContentColor = Color.White,
            disabledContainerColor = Color.White.copy(alpha = 0.4f)
        )
    )
}

@PreviewLightDark()
@Composable
private fun OnPrimaryButtonPreview() {
    NeuroMindTheme {
        Column(
            modifier = Modifier
                .padding(smallValue)
                .background(MaterialTheme.colorScheme.primary)
                .width(300.dp),
            verticalArrangement = Arrangement.Center
        ) {
            OnPrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Primary", onClick = {})
            Spacer(modifier = verticalSmallPadding)
            OnPrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                text = "Primary", onClick = {})
            Spacer(modifier = verticalSmallPadding)
            OnPrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                loading = true,
                text = "Primary", onClick = {})
        }
    }
}