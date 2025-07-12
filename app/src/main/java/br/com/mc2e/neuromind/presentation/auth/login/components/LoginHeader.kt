import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.mc2e.neuromind.R
import br.com.mc2e.neuromind.presentation.shared.components.containers.DefaultPageHeader
import br.com.mc2e.neuromind.presentation.shared.components.icons.IconCircleContainer
import br.com.mc2e.neuromind.ui.theme.NeuroMindTheme
import br.com.mc2e.neuromind.ui.theme.verticalMediumPadding

@Composable
fun LoginHeader() {
    DefaultPageHeader {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconCircleContainer(
                modifier = Modifier.size(44.dp),
                iconId = R.drawable.outline_brain_icon,
                iconDescription = stringResource(R.string.brain_logo_description)
            )
            Spacer(modifier = verticalMediumPadding)
            Text(
                text = stringResource(R.string.app_name),
                style = MaterialTheme.typography.displayMedium.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            )
            Text(
                text = stringResource(R.string.welcome_back),
                style = MaterialTheme.typography.bodyLarge.copy(
                    color = MaterialTheme.colorScheme.onPrimary
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginHeaderPreview() {
    NeuroMindTheme {
        LoginHeader()
    }
}