package org.example.project.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.model.Article
import org.example.project.model.Paragraph
import org.example.project.repository.ArticleRepository
import org.example.project.repository.ArticleRepositoryImpl
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

class ArticleViewModel(
    private val repository: ArticleRepository = ArticleRepositoryImpl()
) {

    private val scope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    private val _title: MutableStateFlow<String> = MutableStateFlow("제목")
    private val _mainImagePath: MutableStateFlow<String?> = MutableStateFlow(null)
    private val _paragraphs: MutableStateFlow<List<Paragraph>> = MutableStateFlow(emptyList())

    sealed class SaveResult {
        object Success : SaveResult()
        data class Error(val message: String) : SaveResult()
    }

    private val _saveResult = MutableSharedFlow<SaveResult>()
    val saveResult = _saveResult.asSharedFlow()

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
    fun saveArticle() {
        val article = Article(
            title = _title.value,
            mainImgUri = _mainImagePath.value,
            paragraphs = _paragraphs.value
        )

        // 1. 유효성 검사
        if (article.title.isBlank()) return
        scope.launch {
            try {
                // 2. 저장
                repository.saveArticle(article)
                _saveResult.emit(SaveResult.Success)
            } catch (e: Exception) {
                _saveResult.emit(SaveResult.Error("저장 실패"))
            }
        }
    }

    fun onDispose() {
        scope.cancel()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleScreen(
    viewModel: ArticleViewModel
) {
    LaunchedEffect(Unit) {
        viewModel.saveResult.collect { result ->
            when (result) {
                ArticleViewModel.SaveResult.Success -> {
                    println("저장 성공")
                    // Snackbar / Dialog / StatusBar
                }
                is ArticleViewModel.SaveResult.Error -> {
                    println(result.message)
                }
            }
        }
    }

    // 상태
    val title by viewModel.title.collectAsState()
    val mainImagePath by viewModel.mainImagePath.collectAsState()
    val paragraphs by viewModel.paragraphs.collectAsState()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Article 작성") },
                    actions = {
                        TextButton(onClick = { viewModel.saveArticle() }) {
                            Text("저장")
                        }
                    }
                )
            }
        ) { padding ->
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(padding)
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
}