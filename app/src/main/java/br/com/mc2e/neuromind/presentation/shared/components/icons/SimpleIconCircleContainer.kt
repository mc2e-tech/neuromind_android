package br.com.mc2e.neuromind.presentation.shared.components.icons

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.ui.theme.Purple200

@Composable
fun SimpleIconCircleContainer(
    iconId: Int,
    iconDescription: String,
    modifier: Modifier
) {
    Box(contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(98.dp)) {
            drawCircle(
                color = Purple200,
                radius = size.minDimension / 2
            )
        }
        Icon(
            modifier = modifier,
            painter = painterResource(id = iconId),
            contentDescription = iconDescription,
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SimpleIconCircleContainerPreview() {
    Box(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        SimpleIconCircleContainer(
            iconId = R.drawable.outline_brain_icon,
            iconDescription = "Brain Logo",
            modifier = Modifier.size(44.dp)
        )
    }
}