package ag.sokolov.telepager

import ag.sokolov.telepager.core.designsystem.theme.TelepagerTheme
import ag.sokolov.telepager.ui.TelepagerApp
import ag.sokolov.telepager.ui.rememberTelepagerAppState
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val appState = rememberTelepagerAppState()

            TelepagerTheme {
                TelepagerApp(appState)
            }
        }
    }
}
