package org.example.project

import androidx.compose.runtime.Composable
import org.example.project.navigation.AppNavHost
import org.example.project.ui.screens.ArticleViewModel
import org.example.project.ui.theme.MyApplicationTheme

@Composable
fun Application(viewModel: ArticleViewModel) {
    MyApplicationTheme {
        AppNavHost(viewModel)
    }
}
