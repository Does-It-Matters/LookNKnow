package org.example.project.model

data class Paragraph(
    val id: Long,
    val order: Int,
    val subTitle: String,
    val imageUri: String?,
    val text: String,
)