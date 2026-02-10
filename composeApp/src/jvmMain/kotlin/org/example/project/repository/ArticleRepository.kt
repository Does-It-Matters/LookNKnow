package org.example.project.repository

import org.example.project.model.Article
import org.example.project.model.Paragraph

interface ArticleRepository {
    suspend fun saveArticle(article: Article)
}

class ArticleRepositoryImpl : ArticleRepository {
    override suspend fun saveArticle(article: Article) {
        println(article.title)
        println(article.mainImgUri)
        for(paragraph: Paragraph in article.paragraphs) {
            println(paragraph.subTitle)
            println(paragraph.imageUri)
            println(paragraph.text)
        }
        // Room / DataStore / Network / File
    }
}

