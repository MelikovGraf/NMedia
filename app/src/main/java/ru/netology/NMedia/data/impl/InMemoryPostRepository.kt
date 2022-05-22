package ru.netology.NMedia.data

import androidx.lifecycle.MutableLiveData

class InMemoryPostRepository : PostRepository {

    private var nextID = GENERATED_POSTS_AMOUNT.toLong()

    private val posts
        get() = checkNotNull(data.value) {
            "Check not null"
        }

    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
            Post(
                id = index + 1L,
                author = "Graf Melikov",
                content = "$index. Привет",
                published = "30 April 18:36",
                likes = 0,
                repost = 0,
                likedByMe = false
            )
        }
    )

    override fun like(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it else it.copy(likedByMe = !it.likedByMe, likes = it.likes++)
        }
    }

    override fun repost(postId: Long) {
        data.value = posts.map {
            if (it.id != postId) it
            else it.copy(repost = it.repost + 1)
        }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    override fun edit(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }


    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private fun insert(post: Post) {
        data.value = listOf(
            post.copy(id = ++nextID)
        ) + posts
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 1000
    }

    override fun delete(postId: Long) {
        data.value = posts.filter { it.id != postId }
    }
}