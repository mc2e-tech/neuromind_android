package br.com.mc2e.neuromind.presentation.shared.components.icons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import br.com.mc2e.neuromind.R
import kotlin.math.roundToInt

@Composable
fun SimpleIconAbsolutePosition(
    iconId: Int,
    iconDescription: String,
    iconOffset: Offset
) {
    Box(
        modifier = Modifier
            .absoluteOffset {
                IntOffset(iconOffset.x.roundToInt(), iconOffset.y.roundToInt())
            }
            .size(40.dp)
            .background(
                color = Color.White.copy(alpha = 0.1f),
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = iconDescription,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(21.dp)
        )
    }
}

@Preview
@Composable
private fun SimpleIconAbsolutePositionPreview() {
    Box(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        SimpleIconAbsolutePosition(
            iconId = R.drawable.outline_brain_icon,
            iconDescription = stringResource(R.string.brain_logo_description),
            iconOffset = Offset(
                x = 1f,
                y = 1f
            )
        )
    }
}