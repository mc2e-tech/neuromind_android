package br.com.mc2e.neuromind.presentation.auth.intro

import CanvasAlphaCircle
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.presentation.shared.components.buttons.OnPrimaryButton
import br.com.mc2e.neuromind.presentation.shared.components.buttons.PrimaryButton
import br.com.mc2e.neuromind.presentation.shared.components.icons.SimpleIconAbsolutePosition
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.Purple200
import br.com.mc2e.neuromind.ui.theme.Purple300
import br.com.mc2e.neuromind.ui.theme.Purple400
import br.com.mc2e.neuromind.ui.theme.mediumValue
import br.com.mc2e.neuromind.ui.theme.verticalMediumPadding
import br.com.mc2e.neuromind.ui.theme.verticalSmallPadding
import br.com.mc2e.neuromind.ui.theme.verticalXXLargePadding
import br.com.mc2e.neuromind.ui.theme.verticalXXXXLargePadding
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun IntroScreen(
    modifier: Modifier = Modifier,
    onEvent: (IntroUserInputEvent) -> Unit,
) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Purple400, Purple300),
                )
            )
            .padding(mediumValue),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            val radius = 105.dp
            val density = LocalDensity.current

            CanvasAlphaCircle(diameter = 160, alpha = 0.1f)
            CanvasAlphaCircle(diameter = 120, alpha = 0.1f)
            CanvasAlphaCircle(diameter = 96, alpha = 0.2f)
            Icon(
                modifier = Modifier.size(44.dp),
                painter = painterResource(id = R.drawable.outline_brain_icon),
                contentDescription = stringResource(R.string.brain_logo_description),
                tint = MaterialTheme.colorScheme.onPrimary
            )
            SimpleIconAbsolutePosition(
                iconId = R.drawable.outline_person_icon,
                iconDescription = stringResource(R.string.person_logo_description),
                iconOffset = getIconOffset(-90.0, radius, density)
            )
            SimpleIconAbsolutePosition(
                iconId = R.drawable.outline_people_icon,
                iconDescription = stringResource(R.string.people_logo_description),
                iconOffset = getIconOffset(90.0, radius, density)
            )
            SimpleIconAbsolutePosition(
                iconId = R.drawable.outline_heart_icon,
                iconDescription = stringResource(R.string.heart_logo_description),
                iconOffset = getIconOffset(0.0, radius, density)
            )
        }
        Spacer(modifier = verticalXXXXLargePadding)
        Text(
            stringResource(R.string.app_name),
            style = MaterialTheme.typography.displayMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary
            )
        )
        Spacer(modifier = verticalMediumPadding)
        Text(
            stringResource(R.string.intro_message),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary,
                lineHeight = 22.8.sp
            )
        )
        Spacer(modifier = verticalXXLargePadding)
        OnPrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.init),
            onClick = { onEvent(IntroUserInputEvent.RegisterClicked) }
        )
        Spacer(modifier = verticalSmallPadding)
        PrimaryButton(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.login),
            colors = ButtonDefaults.buttonColors(
                containerColor = Purple200,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContentColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
            ),
            onClick = { onEvent(IntroUserInputEvent.LoginClicked) }
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            stringResource(R.string.from_mc2e),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelMedium.copy(
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f),
            )
        )
        Spacer(
            modifier = Modifier.height(
                WindowInsets.navigationBars.asPaddingValues().calculateBottomPadding()
            ),
        )
    }
}

fun getIconOffset(angle: Double, radius: Dp, density: Density): Offset {
    val radiusPx = with(density) { radius.toPx() }
    return Offset(
        x = (cos(Math.toRadians(angle)) * radiusPx).toFloat(),
        y = (sin(Math.toRadians(angle)) * radiusPx).toFloat()
    )
}

@PreviewLightDark
@Composable
fun IntroScreenPreview() {
    NeuroMindTheme {
        IntroScreen(
            onEvent = {}
        )
    }
}