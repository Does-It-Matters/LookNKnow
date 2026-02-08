package org.example.project.ui.article.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.model.Paragraph
import org.example.project.ui.article.section.AddParagraphButton
import org.example.project.ui.article.section.MainImageSection
import org.example.project.ui.article.section.MainTitleCard
import org.example.project.ui.article.section.ParagraphCard

@Composable
fun ArticleScreen() {
    // Screen State
    var title by remember { mutableStateOf("제목") }
    var mainImagePath by remember { mutableStateOf<String?>(null) }
    var paragraphs by remember {
        mutableStateOf(
            listOf(
                Paragraph(
                    id = System.currentTimeMillis(),
                    order = 1,
                    subTitle = "문단 제목",
                    imageUri = null,
                    text = "단락 내용"
                )
            )
        )
    }

    // UI 구성
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            /* 제목 */
            MainTitleCard(
                title = title,
                onTitleChange = { title = it }
            )

            Spacer(Modifier.height(16.dp))

            /* 메인 이미지 */
            MainImageSection(
                imagePath = mainImagePath,
                onImageSelected = { path ->
                    mainImagePath = path
                }
            )

            Spacer(Modifier.height(24.dp))

            /* 문단 */
            paragraphs.forEachIndexed { index, paragraph ->
                ParagraphCard(
                    paragraph = paragraph,
                    onUpdate = { updated ->
                        paragraphs = paragraphs.toMutableList().also {
                            it[index] = updated
                        }
                    }
                )
            }

            Spacer(Modifier.height(16.dp))

            /* 문단 추가 */
            AddParagraphButton(
                onClick = {
                    paragraphs = paragraphs + Paragraph(
                        id = System.currentTimeMillis(),
                        1,
                        subTitle = title,
                        imageUri = null,
                        text = "text"
                    )
                }
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}
