package ru.netology.NMedia.ViewModel

import androidx.lifecycle.ViewModel
import ru.netology.NMedia.data.PostRepository
import ru.netology.NMedia.data.InMemoryPostRepository
import ru.netology.NMedia.data.Post

class PostViewModel: ViewModel() {
    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    fun onLikeClicked(post: Post) = repository.like(post.id)

    fun onRepostClicked(post: Post) = repository.repost(post.id)
}