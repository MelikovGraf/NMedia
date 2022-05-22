package ru.netology.NMedia.db

import ru.netology.NMedia.data.Post

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likedById(id: Long)
    fun removeById(id: Long)
}