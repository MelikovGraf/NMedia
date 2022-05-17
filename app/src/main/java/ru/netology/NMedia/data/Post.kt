package ru.netology.NMedia.data

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Long,
    var author: String,
    val video: Boolean,
    var content: String,
    var date: String,
    var likes: Int = 0,
    var repost: Int = 0,
    var likedByMe: Boolean = false,
)