package ru.netology.nmedia.data

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    var author: String,
    var content: String,
    var published: String,
    var likes: Int = 0,
    var repost: Int = 0,
    var likedByMe: Boolean = false,
    val video: String? = null
)