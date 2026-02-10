package org.example.project

import androidx.compose.runtime.remember
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.ui.screens.ArticleViewModel

fun main() = application {
    val viewModel = remember { ArticleViewModel() }

    Window(
        title = "LookNKnow",
        onCloseRequest = {
            viewModel.onDispose()
            exitApplication()
        }
    ) {
        Application(viewModel)
    }
}