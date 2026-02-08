package org.example.project.ui.screens

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

fun fakeParagraphs() = listOf(
    Paragraph(
        id = System.currentTimeMillis(),
        order = 1,
        subTitle = "문단 제목",
        imageUri = null,
        text = "단락 내용"
    )
)

@Composable
fun ArticleScreen(
    // ViewModel 주입
) {
    // 상태
    var title by remember { mutableStateOf("제목") }
    var mainImagePath by remember { mutableStateOf<String?>(null) }
    var paragraphs by remember { mutableStateOf(fakeParagraphs()) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // 1. 메인 섹션 (제목 + 이미지)
            ArticleHeaderSection(
                title = title,
                onTitleChange = { title = it },
                imagePath = mainImagePath,
                onImageSelected = { mainImagePath = it }
            )

            Spacer(Modifier.height(24.dp))

            // 2. 문단 섹션 (문단 리스트 + 추가 버튼)
            ArticleBodySection(
                paragraphs = paragraphs,
                onParagraphUpdate = { index, updated ->
                    paragraphs = paragraphs.toMutableList().apply { this[index] = updated }
                },
                onAddParagraph = {
                    paragraphs = paragraphs + Paragraph(
                        id = System.currentTimeMillis(),
                        order = paragraphs.size + 1,
                        subTitle = "",
                        imageUri = null,
                        text = ""
                    )
                }
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}

@Composable
fun ArticleHeaderSection(
    title: String,
    onTitleChange: (String) -> Unit,
    imagePath: String?,
    onImageSelected: (String?) -> Unit
) {
    Column {
        MainTitleCard(title = title, onTitleChange = onTitleChange)
        Spacer(Modifier.height(16.dp))
        MainImageSection(imagePath = imagePath, onImageSelected = onImageSelected)
    }
}

@Composable
fun ArticleBodySection(
    paragraphs: List<Paragraph>,
    onParagraphUpdate: (Int, Paragraph) -> Unit,
    onAddParagraph: () -> Unit
) {
    Column {
        paragraphs.forEachIndexed { index, paragraph ->
            ParagraphCard(
                paragraph = paragraph,
                onUpdate = { updated -> onParagraphUpdate(index, updated) }
            )
            Spacer(Modifier.height(8.dp)) // 문단 간 간격
        }

        Spacer(Modifier.height(16.dp))

        AddParagraphButton(onClick = onAddParagraph)
    }
}