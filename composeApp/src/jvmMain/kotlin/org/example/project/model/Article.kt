package org.example.project.model

data class Article(
    val title: String,
    val mainImgUri: String?,
    val paragraphs: List<Paragraph>,
//    val author: String,
//    val createdAt: String,
//    val updatedAt: String,
)