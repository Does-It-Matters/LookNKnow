package org.example.project

import androidx.compose.runtime.Composable
import org.example.project.navigation.AppNavHost
import org.example.project.ui.theme.MyApplicationTheme

@Composable
fun Application() {
    MyApplicationTheme {
        AppNavHost()
    }
}
