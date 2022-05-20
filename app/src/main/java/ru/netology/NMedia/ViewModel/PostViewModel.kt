package ru.netology.NMedia.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import no.nordicsemi.android.blinky.viewmodels.SingleLiveEvent
import ru.netology.NMedia.Adapter.PostInteractionListener
import ru.netology.NMedia.data.Post
import ru.netology.NMedia.data.PostRepository
import ru.netology.NMedia.data.impl.FailedPrefsPostRepository

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {
    private val repository: PostRepository =
        FailedPrefsPostRepository(application)

    val sharePostContent = SingleLiveEvent<String>()

    val navigateToPostContentEvent = SingleLiveEvent<String>()

    val data by repository::data

    val currentPost = MutableLiveData<Post?>(null)

    fun onAddClicked() {
        navigateToPostContentEvent.call()
    }

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

    override fun onRemoveClicked(post: Post) = repository.delete(post.id)

    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.content
    }

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentEvent.value = post.content
    }

}