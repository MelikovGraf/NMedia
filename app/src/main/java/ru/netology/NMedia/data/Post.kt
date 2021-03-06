package ru.netology.NMedia.data

data class Post(
    val id: Long,
    var author: String,
    var content: String,
    var date: String,
    var likes: Int = 0,
    var repost: Int = 0,
    var likedByMe: Boolean = false,
)