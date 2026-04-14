package com.biathlonapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "cached_news")
data class CachedNews(
    @PrimaryKey
    val id: String,
    val title: String,
    val content: String,
    val date: Long,  // храним как timestamp
    val imageUrl: String?,
    val source: String,
    val vkPostId: Int,
    val likesCount: Int,
    val commentsCount: Int,
    val repostsCount: Int,
    val cachedAt: Long = System.currentTimeMillis()
)