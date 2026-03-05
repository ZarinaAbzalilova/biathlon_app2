package com.biathlonapp.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface VkApiService {

    @GET("method/wall.get")
    suspend fun getWallPosts(
        @Query("owner_id") ownerId: String,
        @Query("count") count: Int = 20
    ): VkWallResponse
}

// Ответы от VK API
data class VkWallResponse(
    val response: VkWallResponseData
)

data class VkWallResponseData(
    val count: Int,
    val items: List<VkPost>
)

data class VkPost(
    val id: Int,
    val date: Long,
    val text: String,
    val attachments: List<VkAttachment>?,
    val likes: VkLikes?,
    val comments: VkComments?,
    val reposts: VkReposts?
)

data class VkAttachment(
    val type: String,
    val photo: VkPhoto?
)

data class VkPhoto(
    val sizes: List<VkPhotoSize>
) {
    fun getMaxSizeUrl(): String? {
        return sizes.maxByOrNull { it.width * it.height }?.url
    }
}

data class VkPhotoSize(
    val url: String,
    val width: Int,
    val height: Int
)

data class VkLikes(val count: Int)
data class VkComments(val count: Int)
data class VkReposts(val count: Int)