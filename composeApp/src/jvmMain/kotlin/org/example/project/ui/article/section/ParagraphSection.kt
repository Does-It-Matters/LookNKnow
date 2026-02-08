package org.example.project.ui.article.section

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.model.Paragraph
import org.example.project.ui.util.DesktopImage

@Composable
fun ParagraphCard(
    paragraph: Paragraph,
    onUpdate: (Paragraph) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            TextField(
                value = paragraph.subTitle,
                onValueChange = {
                    onUpdate(paragraph.copy(subTitle = it))
                },
                placeholder = { Text("서브 타이틀") }
            )

            Spacer(Modifier.height(8.dp))

            // 이미지 경로가 있으면 이미지를 표현
            println("문단 이미지 ${paragraph.imageUri}")
            paragraph.imageUri?.let { path ->
                DesktopImage(
                    imagePath = path,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                )
            } ?: run {
                Button(
                    onClick = {
                        println("Desktop 파일 선택, 데이터베이스에 저장, 데이터베이스에 저장된 이미지 표현")
                    }
                ) {
                    Text("이미지 선택")
                }
            }

            Spacer(Modifier.height(8.dp))

            TextField(
                value = paragraph.text,
                onValueChange = {
                    onUpdate(paragraph.copy(text = it))
                },
                placeholder = { Text("내용을 입력하세요") },
                modifier = Modifier.height(120.dp)
            )
        }
    }
}

//
//@Composable
//fun ArticleImage(
//    imagePath: String?,
//    modifier: Modifier
//) {
//    println("Image selected: $imagePath")
//    if (imagePath == null) return
//
//    DesktopImage(
//        imagePath = imagePath,
//        modifier = modifier
//    )
//}
