package br.com.mc2e.neuromind.presentation.components.containers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mc2e.neuromind.ui.theme.Purple300
import br.com.mc2e.neuromind.ui.theme.Purple400
import br.com.mc2e.neuromind.ui.theme.horizontalPaddingValue

@Composable
fun DefaultPageHeader(
    content: @Composable ColumnScope.() -> Unit
) {
    val curveHeight = 40f
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .graphicsLayer {
                clip = true
                shape = GenericShape { size, _ ->
                    moveTo(0f, 0f)
                    lineTo(0f, size.height - curveHeight)
                    quadraticBezierTo(
                        size.width / 2, size.height + curveHeight,
                        size.width, size.height - curveHeight
                    )
                    lineTo(size.width, 0f)
                    close()
                }
            }
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Purple400, Purple300),
                )
            )
            .padding(horizontal = horizontalPaddingValue),
        contentAlignment = Alignment.Center,
    ) {
        Column {
            Spacer(modifier = Modifier.height(statusBarPadding))
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPageHeaderPreview() {
    DefaultPageHeader {}
}