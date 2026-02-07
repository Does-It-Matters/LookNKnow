package org.example.project.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp

import androidx.compose.runtime.mutableStateListOf
import org.example.project.ui.theme.Navy40

enum class PanDirection {
    LEFT, RIGHT, TOP, BOTTOM
}


@Composable
fun CanvasScreen() {
    // mutableStateListOf를 써야 개별 아이템 변경 시 recomposition 발생
    val briefs = remember {
        mutableStateListOf(
            StickyArticleBrief(
                id = "1",
                x = 100f,
                y = 100f,
                text = "첫번째 아티클 제목 수는 ..."
            ),
            StickyArticleBrief(
                id = "2",
                x = 400f,
                y = 300f,
                text = "1234567890"
            )
        )
    }

    // InfiniteCanvas 연결
    InfiniteCanvas(
        briefs = briefs,
        onNoteMove = { id, dx, dy ->
            // 이동할 노트 찾기
            val index = briefs.indexOfFirst { it.id == id }
            if (index != -1) {
                val old = briefs[index]

                // 좌표 기준으로 업데이트
                briefs[index] = old.copy(
                    x = old.x + dx,
                    y = old.y + dy
                )
            }
        }
    )
}

@Composable
fun InfiniteCanvas(
    briefs: List<StickyArticleBrief>,
    onNoteMove: (id: String, dx: Float, dy: Float) -> Unit
) {
    var scale by remember { mutableStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .pointerInput(Unit) {
                detectTransformGestures { _, pan, zoom, _ ->
                    scale = (scale * zoom).coerceIn(0.5f, 3f)
                    offset += pan
                }
            }
            .graphicsLayer {
                translationX = offset.x
                translationY = offset.y
                scaleX = scale
                scaleY = scale
            }
    ) {
        briefs.forEach { brief ->
            StickyArticleBrief(
                brief = brief,
                scale = scale,
                onMove = onNoteMove
            )
        }
    }
}

@Composable
fun StickyArticleBrief(
    brief: StickyArticleBrief,
    scale: Float,
    onMove: (String, Float, Float) -> Unit
) {
    Card(
        modifier = Modifier
            .offset { IntOffset(brief.x.toInt(), brief.y.toInt()) }
            .size(200.dp)
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    // scale 보정 중요
                    onMove(
                        brief.id,
                        dragAmount.x / scale,
                        dragAmount.y / scale
                    )
                }
            },
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Navy40)
                .padding(12.dp)
        ) {
            Text(
                brief.text,
                color = Color.White
            )
        }
    }
}
data class StickyArticleBrief(
    val id: String,
    val x: Float,
    val y: Float,
    val text: String
)
data class CanvasState(
    val scale: Float,
    val offsetX: Float,
    val offsetY: Float
)
