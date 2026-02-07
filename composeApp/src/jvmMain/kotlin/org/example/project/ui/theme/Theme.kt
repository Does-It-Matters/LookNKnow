package org.example.project.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorScheme = lightColorScheme(
    primary = Navy40,
    onPrimary = Color.White,

    secondary = NavyAccent40,
    onSecondary = Color.White,

    background = Color(0xFFF8F9FB),
    surface = Color(0xFFFFFFFF),
    onSurface = Color(0xFF0D1B2A)
)

val DarkColorScheme = darkColorScheme(
    primary = Navy80,
    onPrimary = Color(0xFF0D1B2A),

    secondary = NavyAccent80,
    onSecondary = Color(0xFF0D1B2A),

    background = Color(0xFF0B1320),
    surface = Color(0xFF0D1B2A),
    onSurface = Color(0xFFE6EAF0)
)


@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
