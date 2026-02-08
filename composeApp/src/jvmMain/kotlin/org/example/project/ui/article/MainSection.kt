package org.example.project.ui.article

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.ui.util.DesktopImage

// Article 제목
@Composable
fun MainTitleCard(
    title: String,
    onTitleChange: (String) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        TextField(
            value = title,
            onValueChange = onTitleChange,
            textStyle = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(16.dp),
            placeholder = { Text("제목을 입력하세요") }
        )
    }
}

// Article 대표 이미지
@Composable
fun MainImageSection(
    imagePath: String?,
    onImageSelected: (String) -> Unit
) {
    Column {
        imagePath?.let {
            DesktopImage(
                imagePath = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        } ?: run {
            Button(
                onClick = {
                    println("여기서 이미지를 선택하여 해당 파일 경로명을 전달")
                    println("Desktop 파일 선택, 데이터베이스에 저장, 데이터베이스에 저장된 이미지 표현")
                },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("이미지 추가")
            }
        }
    }
}

// 문단 추가
@Composable
fun AddParagraphButton(onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text("Paragraph 추가")
    }
}

