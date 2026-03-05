package com.biathlonapp.data.model

import java.io.Serializable
import java.util.*

data class News(
    val id: String,
    val title: String,
    val content: String,
    val date: Date,
    val imageUrl: String?,
    val source: NewsSource,
    val vkPostId: Int,
    val likesCount: Int = 0,
    val commentsCount: Int = 0,
    val repostsCount: Int = 0
) : Serializable

enum class NewsSource {
    VK_SBR,           // Союз биатлонистов России
    VK_BIATHLON_ONLINE, // Биатлон онлайн
    VK_NORWAY_TEAM,    // Норвежская сборная
    OTHER
}