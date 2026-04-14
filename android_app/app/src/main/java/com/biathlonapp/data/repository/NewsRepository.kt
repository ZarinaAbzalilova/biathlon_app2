package com.biathlonapp.data.repository

import android.content.Context
import android.util.Log
import com.biathlonapp.data.api.VkApiService
import com.biathlonapp.data.api.VkPost
import com.biathlonapp.data.local.CachedNews
import com.biathlonapp.data.local.FavoriteDatabase
import com.biathlonapp.data.model.News
import com.biathlonapp.data.model.NewsSource
import com.biathlonapp.utils.ErrorHandler
import com.biathlonapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*

class NewsRepository(private val context: Context) {

    private val database = FavoriteDatabase.getInstance(context)
    private val newsDao = database.newsDao()

    private val clientId = "54470422"
    private val clientSecret = "gOOa0qgwaAgBEsP5pQLB"

    private val api: VkApiService by lazy {
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val original = chain.request()
                val url = original.url.newBuilder()
                    .addQueryParameter("client_id", clientId)
                    .addQueryParameter("client_secret", clientSecret)
                    .addQueryParameter("v", "5.131")
                    .build()
                val request = original.newBuilder()
                    .url(url)
                    .build()
                chain.proceed(request)
            }
            .build()

        Retrofit.Builder()
            .baseUrl("https://api.vk.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VkApiService::class.java)
    }

    private val communities = mapOf(
        NewsSource.VK_SBR to "-636950"
    )

    suspend fun getAllNews(forceRefresh: Boolean = false): Result<List<News>> = withContext(Dispatchers.IO) {
        // 1. Сначала пробуем загрузить из кэша
        if (!forceRefresh) {
            val cachedNews = getCachedNews()
            if (cachedNews.isNotEmpty()) {
                Log.d("NewsRepository", "📦 Loaded ${cachedNews.size} news from cache")
                refreshCacheInBackground()
                return@withContext Result.Success(cachedNews)
            }
        }

        // 2. Если кэш пуст или forceRefresh=true, загружаем с сервера
        try {
            val allNews = mutableListOf<News>()
            var lastError: Exception? = null

            for ((source, ownerId) in communities) {
                try {
                    val result = getNewsFromVk(source, 20)
                    when (result) {
                        is Result.Success -> {
                            allNews.addAll(result.data)
                        }
                        is Result.Error -> {
                            lastError = result.exception
                        }
                    }
                } catch (e: Exception) {
                    lastError = e
                }
            }

            if (allNews.isNotEmpty()) {
                allNews.sortByDescending { it.date }
                saveNewsToCache(allNews)
                Result.Success(allNews)
            } else if (lastError != null) {
                Result.Error(lastError)
            } else {
                Result.Success(emptyList())
            }
        } catch (e: Exception) {
            Log.e("NewsRepository", "Error loading news", e)
            Result.Error(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }

    private suspend fun getNewsFromVk(
        source: NewsSource,
        count: Int = 20
    ): Result<List<News>> {
        return try {
            val ownerId = communities[source]
            if (ownerId == null) {
                return Result.Success(emptyList())
            }

            val response = api.getWallPosts(
                ownerId = ownerId,
                count = count
            )

            if (response.error != null) {
                val error = response.error
                return Result.Error(Exception("VK API error: ${error.error_msg}"))
            }

            val responseData = response.response
            if (responseData == null) {
                return Result.Error(Exception("Empty response from VK API"))
            }

            val news = responseData.items
                .filter { post -> post.text.isNotEmpty() }  // ← только с текстом
                .map { post ->
                    convertVkPostToNews(post, source)
            }

            Result.Success(news)
        } catch (e: Exception) {
            Result.Error(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }

    private suspend fun getCachedNews(): List<News> {
        return try {
            val cached = newsDao.getAllNews()
            cached.map { cachedNews ->
                News(
                    id = cachedNews.id,
                    title = cachedNews.title,
                    content = cachedNews.content,
                    date = Date(cachedNews.date),
                    imageUrl = cachedNews.imageUrl,
                    source = NewsSource.valueOf(cachedNews.source),
                    vkPostId = cachedNews.vkPostId,
                    likesCount = cachedNews.likesCount,
                    commentsCount = cachedNews.commentsCount,
                    repostsCount = cachedNews.repostsCount
                )
            }
        } catch (e: Exception) {
            Log.e("NewsRepository", "Error reading cache", e)
            emptyList()
        }
    }

    private suspend fun saveNewsToCache(news: List<News>) {
        try {
            val cachedNewsList = news.map { newsItem ->
                CachedNews(
                    id = newsItem.id,
                    title = newsItem.title,
                    content = newsItem.content,
                    date = newsItem.date.time,
                    imageUrl = newsItem.imageUrl,
                    source = newsItem.source.name,
                    vkPostId = newsItem.vkPostId,
                    likesCount = newsItem.likesCount,
                    commentsCount = newsItem.commentsCount,
                    repostsCount = newsItem.repostsCount
                )
            }
            newsDao.insertAll(cachedNewsList)
            Log.d("NewsRepository", "💾 Saved ${cachedNewsList.size} news to cache")
        } catch (e: Exception) {
            Log.e("NewsRepository", "Error saving to cache", e)
        }
    }

    private suspend fun refreshCacheInBackground() {
        try {
            val allNews = mutableListOf<News>()
            for ((source, ownerId) in communities) {
                val result = getNewsFromVk(source, 20)
                when (result) {
                    is Result.Success -> {
                        allNews.addAll(result.data)
                    }
                    else -> {}
                }
            }
            if (allNews.isNotEmpty()) {
                allNews.sortByDescending { it.date }
                saveNewsToCache(allNews)
                Log.d("NewsRepository", "🔄 Background cache refresh completed")
            }
        } catch (e: Exception) {
            Log.e("NewsRepository", "Background refresh failed", e)
        }
    }

    private fun convertVkPostToNews(post: VkPost, source: NewsSource): News {
        val date = Date(post.date * 1000)

        var imageUrl: String? = null
        post.attachments?.firstOrNull { it.type == "photo" }?.let { attachment ->
            imageUrl = attachment.photo?.getMaxSizeUrl()
        }

        val title = if (post.text.isNotEmpty()) {
            post.text.take(100).trim() + if (post.text.length > 100) "..." else ""
        } else {
            "Новость биатлона"
        }

        return News(
            id = "${source.name}_${post.id}",
            title = title,
            content = post.text.ifEmpty { "Новость без текста" },
            date = date,
            imageUrl = imageUrl,
            source = source,
            vkPostId = post.id,
            likesCount = post.likes?.count ?: 0,
            commentsCount = post.comments?.count ?: 0,
            repostsCount = post.reposts?.count ?: 0
        )
    }
}