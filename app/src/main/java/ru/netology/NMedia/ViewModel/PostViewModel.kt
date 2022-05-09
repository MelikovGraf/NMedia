package ru.netology.NMedia.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.NMedia.Adapter.PostInteractionListener
import ru.netology.NMedia.data.InMemoryPostRepository
import ru.netology.NMedia.data.Post
import ru.netology.NMedia.data.PostRepository

class PostViewModel : ViewModel(), PostInteractionListener {
    private val repository: PostRepository = InMemoryPostRepository()

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)

    fun onSaveClicked(content: String) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Graf Melikov",
            content = "Hello",
            date = "06 May 17:36",
            likes = 0,
            repost = 0,
            likedByMe = false
        )
        repository.save(post)
        currentPost.value = null
    }

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onRepostClicked(post: Post) = repository.repost(post.id)

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onEditClicked(post: Post) {
        currentPost.value = post
    }
}