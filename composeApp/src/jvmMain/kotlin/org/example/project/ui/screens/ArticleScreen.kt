package org.example.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.model.Paragraph
import org.example.project.ui.article.section.ArticleBodySection
import org.example.project.ui.article.section.ArticleHeaderSection

fun fakeParagraphs() = listOf(
    Paragraph(
        id = System.currentTimeMillis(),
        order = 1,
        subTitle = "문단 제목",
        imageUri = null,
        text = "단락 내용"
    )
)

class ArticleViewModel {
    private val _title: MutableStateFlow<String> = MutableStateFlow("제목")
    private val _mainImagePath: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _paragraphs: MutableStateFlow<List<Paragraph>> = MutableStateFlow(emptyList())

    val title: StateFlow<String> = _title.asStateFlow()
    val mainImagePath: StateFlow<String?> = _mainImagePath.asStateFlow()
    val paragraphs = _paragraphs.asStateFlow()

    fun updateTitle(newTitle: String) { _title.value = newTitle }
    fun updateMainImage(path: String?) { _mainImagePath.value = path }
    fun updateParagraph(index: Int, updated: Paragraph) {
        _paragraphs.update { list ->
            list.toMutableList().apply { this[index] = updated }
        }
    }
    fun addParagraph() {
        _paragraphs.update { list ->
            list + Paragraph(
                id = System.currentTimeMillis(),
                order = list.size + 1,
                subTitle = "",
                imageUri = null,
                text = ""
            )
        }
    }
}

@Composable
fun ArticleScreen(
    viewModel: ArticleViewModel
) {
    // 상태
    val title by viewModel.title.collectAsState()
    val mainImagePath by viewModel.mainImagePath.collectAsState()
    val paragraphs by viewModel.paragraphs.collectAsState()

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
                onTitleChange = viewModel::updateTitle,
                imagePath = mainImagePath,
                onImageSelected = viewModel::updateMainImage,
            )

            Spacer(Modifier.height(24.dp))

            // 2. 문단 섹션 (문단 리스트 + 추가 버튼)
            ArticleBodySection(
                paragraphs = paragraphs,
                onParagraphUpdate = viewModel::updateParagraph,
                onAddParagraph = viewModel::addParagraph
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}