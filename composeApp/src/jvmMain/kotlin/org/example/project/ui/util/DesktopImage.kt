package org.example.project.ui.util

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import org.jetbrains.compose.resources.decodeToImageBitmap
import java.io.FileInputStream

// 이미지 경로를 제공받으면 이미지를 표현하는 함수
@Composable
fun DesktopImage(
    imagePath: String,
    modifier: Modifier = Modifier
) {
    println("이미지 경로: $imagePath")
    val imageBitmap: ImageBitmap? = remember(imagePath) {
        runCatching {
            FileInputStream(imagePath).use {
                it.readAllBytes().decodeToImageBitmap()
            }
        }.getOrNull()
    }

    imageBitmap?.let {
        Image(
            bitmap = it,
            contentDescription = null,
            modifier = modifier
        )
    }
}