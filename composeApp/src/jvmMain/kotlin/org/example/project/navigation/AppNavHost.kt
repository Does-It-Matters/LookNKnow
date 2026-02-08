package org.example.project.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.example.project.ui.components.PrimaryButton
import org.example.project.ui.screens.ArticleScreen
import org.example.project.ui.screens.ArticleViewModel
import org.example.project.ui.screens.CanvasScreen
import org.example.project.ui.screens.HomeScreen

@Composable
fun AppNavHost() {
    var currentScreen by remember { mutableStateOf<Screen>(Screen.Article) }
    val articleViewModel = remember { ArticleViewModel() }

    Scaffold(
        topBar = {
            AppTopBar(
                screen = currentScreen,
                onBackClick = {
                    currentScreen = Screen.Home
                }
            )
        }
    ) { paddingValues ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            LeftHoverPanel()
            when (val screen = currentScreen) {
                is Screen.Home -> HomeScreen()
                Screen.Article -> ArticleScreen(articleViewModel)
                Screen.Canvas -> CanvasScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    screen: Screen,
    onBackClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Surface(
        shadowElevation = 5.dp,
        color = Color.White
    ) {
        Column {
            TopAppBar(
                title = {},
                navigationIcon = {
                    if (screen !is Screen.Home) {
                        PrimaryButton(
                            "Home",
                            onClick = { onBackClick() }
                        )
                    }
                },

                actions = {
                    SearchAction(
                        query = searchQuery,
                        onQueryChange = { searchQuery = it },
                        onSearch = {
                            println("Search: $searchQuery")
                        }
                    )
                },

                modifier = Modifier.height(48.dp), // 바 높이
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )


            )
            Divider(
                color = Color(0xFF0A1E3F), // 짙은 네이비
                thickness = 2.dp
            )
        }
    }
}

@Composable
fun SearchAction(
    query: String,
    onQueryChange: (String) -> Unit,
    onSearch: () -> Unit,
    isFocused: Boolean = false
) {
    var isFocused by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = Color.Black
            ),
            decorationBox = { innerTextField ->
                Column {
                    innerTextField()

                    Spacer(modifier = Modifier.height(4.dp))

                    Divider(
                        thickness = 1.dp,
                        color = if (isFocused) Color(0xFF0A1E3F) else Color.LightGray
                    )
                }
            },
            singleLine = true,
            modifier = Modifier
//                .height(36.dp)
                .width(400.dp)
                .onFocusChanged { isFocused = it.isFocused }
        )

        Spacer(Modifier.width(8.dp))

        PrimaryButton(
            "search",
            onClick = { onSearch() },
        )
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LeftHoverPanel() {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    val panelWidth by animateDpAsState(
        targetValue = if (isHovered) 200.dp else 48.dp,
        animationSpec = tween(
            durationMillis = 250,
            easing = FastOutSlowInEasing
        )
    )

    Surface(
        modifier = Modifier
            .fillMaxHeight()
            .width(panelWidth)
            .hoverable(interactionSource),
        color = Color(0xFFF8F9FB),
        shadowElevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp)
        ) {
            Text("")
            if (isHovered) {
                Spacer(Modifier.height(12.dp))
                Text("Home")
                Text("Article")
                Text("Canvas")
            }
        }
    }
}
