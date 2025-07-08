package br.com.mc2e.neuromind.presentation.shared.components.buttons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.smallValue
import br.com.mc2e.neuromind.ui.theme.verticalSmallPadding

@Composable
fun PrimaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    loading: Boolean = false,
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(52.dp),
        shape = RoundedCornerShape(8.dp),
        enabled = enabled && !loading,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
        )
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp,
                modifier = Modifier.size(20.dp)
            )
        } else {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@PreviewLightDark()
@Composable
private fun PrimaryButtonPreview() {
    NeuroMindTheme {
        Column(
            modifier = Modifier
                .padding(smallValue)
                .background(MaterialTheme.colorScheme.background)
                .width(300.dp),
            verticalArrangement = Arrangement.Center
        ) {
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = "Primary", onClick = {})
            Spacer(modifier = verticalSmallPadding)
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = false,
                text = "Primary", onClick = {})
            Spacer(modifier = verticalSmallPadding)
            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                loading = true,
                text = "Primary", onClick = {})
        }
    }
}