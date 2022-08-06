package ru.netology.nmedia.data

import kotlinx.coroutines.Job
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class Post(
    val id: Long,
    var author: String,
    var content: String,
    var published: String = Date().toString(),
    var likes: Int = 0,
    var repost: Int = 0,
    var likedByMe: Boolean = false,
    val video: String? = "https://www.youtube.com/watch?v=WhWc3b3KhnY"
)