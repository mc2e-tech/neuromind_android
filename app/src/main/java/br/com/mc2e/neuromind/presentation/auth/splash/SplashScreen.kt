import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.Purple300
import br.com.mc2e.neuromind.ui.theme.Purple400
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
) {
    val infiniteTransition = rememberInfiniteTransition(label = "orbit")

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(40000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "angle"
    )

    val radius = 80.dp
    val centerX = with(LocalDensity.current) { radius.toPx() }
    val centerX2 = centerX + 75

    val icon1Offset = Offset(
        x = (cos(Math.toRadians(angle.toDouble())) * centerX).toFloat(),
        y = (sin(Math.toRadians(angle.toDouble())) * centerX).toFloat()
    )

    val icon2Offset = Offset(
        x = (cos(Math.toRadians((angle + 120).toDouble())) * centerX2).toFloat(),
        y = (sin(Math.toRadians((angle + 120).toDouble())) * centerX2).toFloat()
    )

    val icon3Offset = Offset(
        x = (cos(Math.toRadians((angle + 240).toDouble())) * centerX2).toFloat(),
        y = (sin(Math.toRadians((angle + 240).toDouble())) * centerX2).toFloat()
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Purple400, Purple300),
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        CanvasAlphaCircle(diameter = 256, alpha = 0.05f)
        CanvasAlphaCircle(diameter = 208, alpha = 0.1f)
        CanvasAlphaCircle(diameter = 160, alpha = 0.2f)
        CanvasAlphaCircle(diameter = 112, alpha = 0.3f)
        CanvasAlphaCircle(diameter = 80, alpha = 0.4f)
        Icon(
            modifier = Modifier.size(44.dp),
            painter = painterResource(id = R.drawable.outline_brain_icon),
            contentDescription = stringResource(R.string.brain_logo_description),
            tint = MaterialTheme.colorScheme.onPrimary
        )
        IconWithCircle(
            iconId = R.drawable.outline_person_icon,
            iconOffset = icon1Offset
        )
        IconWithCircle(
            iconId = R.drawable.outline_people_icon,
            iconOffset = icon2Offset
        )
        IconWithCircle(
            iconId = R.drawable.outline_heart_icon,
            iconOffset = icon3Offset
        )
    }
}

@Composable
fun IconWithCircle(iconId: Int, iconOffset: Offset) {
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
            contentDescription = stringResource(R.string.brain_logo_description),
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(21.dp)
        )
    }
}

@PreviewLightDark()
@Composable
fun SplashScreenPreview() {
    NeuroMindTheme {
        SplashScreen()
    }
}