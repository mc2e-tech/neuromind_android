package br.com.mc2e.neuromind.presentation.register.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.presentation.components.containers.DefaultPageHeader
import br.com.mc2e.neuromind.presentation.components.containers.SimpleIconCircleContainer
import br.com.mc2e.neuromind.ui.theme.verticalMediumPadding
import br.com.mc2e.neuromind.ui.theme.verticalXLargePadding
import br.com.mc2e.neuromind.ui.theme.verticalXXLargePadding

@Composable
fun RegisterHeader(
    iconId: Int,
    iconDescription: String,
    modifier: Modifier,
    title: String,
    description: String,
    targetProgress: Float,
) {
    DefaultPageHeader {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LinearProgressIndicator(
                progress = { targetProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(5.5.dp),
                color = Color.White,
                trackColor = Color.LightGray.copy(alpha = 0.4f),
                strokeCap = StrokeCap.Round
            )
            Spacer(modifier = verticalXXLargePadding)
            SimpleIconCircleContainer(
                iconId = iconId,
                iconDescription = iconDescription,
                modifier = modifier
            )
            Spacer(modifier = verticalMediumPadding)
            Text(
                text = title,
                style = MaterialTheme.typography.displaySmall.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            )
            Spacer(modifier = verticalXLargePadding)
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            )
        }
    }
}


@PreviewLightDark()
@Composable
private fun PreviewRegisterHeader() {
    RegisterHeader(
        iconId = R.drawable.outline_brain_icon,
        iconDescription = stringResource(R.string.brain_logo_description),
        modifier = Modifier.size(44.dp),
        title = stringResource(R.string.onboarding_start),
        description = stringResource(R.string.name_prompt_description),
        targetProgress = 0.35f
    )
}

