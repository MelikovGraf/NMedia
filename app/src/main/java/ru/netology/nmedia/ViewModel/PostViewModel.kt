package ru.netology.nmedia.ViewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import no.nordicsemi.android.blinky.viewmodels.SingleLiveEvent
import ru.netology.nmedia.Adapter.PostInteractionListener
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.data.impl.PostRepositoryImpl
import ru.netology.nmedia.db.AppDB
import ru.netology.nmedia.ui.FeedFragment

class PostViewModel(
    application: Application
) : AndroidViewModel(application), PostInteractionListener {
    private val repository: PostRepository =
        PostRepositoryImpl(
            dao = AppDB.getInstance(
                context = application
            ).postDao
        )

    val sharePostContent = SingleLiveEvent<String>()

    val navigateToPostContentEvent = SingleLiveEvent<String>()

    val navigateToReceptEvent = SingleLiveEvent<String>()

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
            published = "06 May 17:36",
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

    fun onViewClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentEvent.value = post.content
    }

    fun onEditsClicked(content: String) {
        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            content = content
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Graf Melikov",
            content = "Hello",
            published = "06 May 17:36",
            likes = 0,
            repost = 0,
            likedByMe = false
        )
        repository.save(post)
        currentPost.value = null
    }

}