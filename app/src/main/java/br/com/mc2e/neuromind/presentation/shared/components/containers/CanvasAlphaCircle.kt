import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme

@Composable
fun CanvasAlphaCircle(
    diameter: Int,
    alpha: Float
) {
    Canvas(modifier = Modifier.size(diameter.dp)) {
        drawCircle(
            color = Color.White.copy(alpha = alpha),
            radius = size.minDimension / 2
        )
    }
}

@PreviewLightDark()
@Composable
fun CanvasAlphaCirclePreview() {
    NeuroMindTheme {
        CanvasAlphaCircle(
            diameter = 160,
            alpha = 0.1f
        )
    }
}