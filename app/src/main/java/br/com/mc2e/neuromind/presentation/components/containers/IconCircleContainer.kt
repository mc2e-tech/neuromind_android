package br.com.mc2e.neuromind.presentation.components.containers

import CanvasAlphaCircle
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

@Composable
fun IconCircleContainer(
    iconId: Int,
    iconDescription: String,
    modifier: Modifier
) {
    Box(contentAlignment = Alignment.Center) {
        CanvasAlphaCircle(diameter = 160, alpha = 0.1f)
        CanvasAlphaCircle(diameter = 120, alpha = 0.1f)
        CanvasAlphaCircle(diameter = 96, alpha = 0.2f)
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
fun IconCircleContainerPreview() {
    Box(
        modifier = Modifier.background(
            color = MaterialTheme.colorScheme.primary
        )
    ) {
        IconCircleContainer(
            iconId = R.drawable.outline_brain_icon,
            iconDescription = "Brain Logo",
            modifier = Modifier.size(44.dp)
        )
    }
}