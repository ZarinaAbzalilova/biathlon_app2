package com.biathlonapp.data.repository

import android.util.Log
import com.biathlonapp.data.api.VkApiService
import com.biathlonapp.data.api.VkPost
import com.biathlonapp.data.model.News
import com.biathlonapp.data.model.NewsSource
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.biathlonapp.utils.ErrorHandler
import java.util.*

class NewsRepository {

    // Твои данные из кабинета VK
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
        // ...

        Retrofit.Builder()
            .baseUrl("https://api.vk.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VkApiService::class.java)
    }
    private val communities = mapOf(
        NewsSource.VK_SBR to "-636950"  // Союз биатлонистов России
        // Добавь другие сообщества по мере необходимости
    )


    suspend fun getNewsFromVk(
        source: NewsSource,
        count: Int = 20
    ): Result<List<News>> {
        return try {
            val ownerId = communities[source]
            if (ownerId == null) {
                Log.e("NewsRepository", "No ownerId for source $source")
                return Result.success(emptyList())
            }

            Log.d("NewsRepository", "Making API request to VK: ownerId=$ownerId, count=$count")

            val response = api.getWallPosts(
                ownerId = ownerId,
                count = count
            )

            if (response.error != null) {
                val error = response.error
                Log.e("NewsRepository", "VK API error: ${error.error_code} - ${error.error_msg}")
                return Result.failure(Exception("VK API error: ${error.error_msg}"))
            }

            val responseData = response.response
            if (responseData == null) {
                Log.e("NewsRepository", "VK API response is null")
                return Result.failure(Exception("Empty response from VK API"))
            }

            Log.d("NewsRepository", "VK API response received. Items count: ${responseData.items.size}")

            val news = responseData.items.map { post ->
                convertVkPostToNews(post, source)
            }

            Result.success(news)
        } catch (e: Exception) {
            Log.e("NewsRepository", "Error loading news from $source", e)
            // ← Добавляем понятное сообщение
            Result.failure(Exception(ErrorHandler.getErrorMessage(e)))
        }
    }

    // Временный метод для проверки ID
    suspend fun checkCommunityId() {
        try {
            val ownerId = communities[NewsSource.VK_SBR] ?: return
            Log.d("NewsRepository", "Checking community with ownerId: $ownerId")

            val response = api.getWallPosts(ownerId = ownerId, count = 1)

            if (response.error != null) {
                Log.e("NewsRepository", "Error: ${response.error.error_code} - ${response.error.error_msg}")
            } else {
                Log.d("NewsRepository", "Success! Community exists")
            }
        } catch (e: Exception) {
            Log.e("NewsRepository", "Check failed", e)
        }
    }
    suspend fun getAllNews(): Result<List<News>> {
        val allNews = mutableListOf<News>()
        var lastError: Exception? = null

        Log.d("NewsRepository", "🟢 Starting to load news")
        Log.d("NewsRepository", "Client ID: $clientId")
        Log.d("NewsRepository", "Client Secret: ${clientSecret.take(3)}...")

        communities.forEach { (source, ownerId) ->
            Log.d("NewsRepository", "Loading from $source with ownerId=$ownerId")
            try {
                val result = getNewsFromVk(source, 10)
                if (result.isSuccess) {
                    val newsList = result.getOrNull()
                    Log.d("NewsRepository", "✅ Loaded ${newsList?.size} news from $source")
                    newsList?.let { allNews.addAll(it) }
                } else {
                    val error = result.exceptionOrNull()
                    Log.e("NewsRepository", "❌ Failed to load from $source", error)
                    lastError = error as? Exception ?: Exception("Unknown error")
                }
            } catch (e: Exception) {
                Log.e("NewsRepository", "❌ Exception loading from $source", e)
                lastError = e
            }
        }

        Log.d("NewsRepository", "📊 Total news loaded: ${allNews.size}")

        // Сортируем по дате (свежие сверху)
        allNews.sortByDescending { it.date }

        return if (allNews.isNotEmpty()) {
            Result.success(allNews)
        } else if (lastError != null) {
            Result.failure(lastError!!)
        } else {
            Result.success(emptyList())
        }
    }
    private fun convertVkPostToNews(post: VkPost, source: NewsSource): News {
        val date = Date(post.date * 1000) // VK timestamp в секундах

        // Получаем первое изображение, если есть
        var imageUrl: String? = null
        post.attachments?.firstOrNull { it.type == "photo" }?.let { attachment ->
            imageUrl = attachment.photo?.getMaxSizeUrl()
        }

        // Обрезаем текст для заголовка (первые 100 символов)
        val title = if (post.text.isNotEmpty()) {
            post.text.take(100).trim() + if (post.text.length > 100) "..." else ""
        } else {
            "Новость биатлона"
        }

        return News(
            id = "${source.name}_${post.id}",
            title = title,
            content = post.text.ifEmpty() { "Новость без текста" },
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