package ag.sokolov.telepager.feature.test

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TestScreen(modifier: Modifier = Modifier) {
    Box(
        modifier.fillMaxSize()
    ) {
        Text("Test")
    }
}
