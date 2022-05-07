package ru.netology.NMedia.data

import androidx.lifecycle.LiveData

interface PostRepository {

    val data: LiveData<List<Post>>

    fun like(postId: Long)

    fun repost(postId: Long)
}