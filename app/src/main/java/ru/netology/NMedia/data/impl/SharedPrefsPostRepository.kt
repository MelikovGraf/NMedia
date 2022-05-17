package ru.netology.NMedia.data.impl

import android.app.Application
import android.content.Context
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ru.netology.NMedia.data.Post
import ru.netology.NMedia.data.PostRepository
import kotlin.properties.Delegates

class SharedPrefsPostRepository(
    application: Application,
) : PostRepository {

    private val prefs = application.getSharedPreferences(
        "repo", Context.MODE_PRIVATE
    )
    private var nextID: Long by Delegates.observable(
        prefs.getLong(NEXT_ID, 0L)
    ) {_,_,newValue ->
        prefs.edit { putLong(NEXT_ID, newValue) }
    }

    private var posts
        get() = checkNotNull(data.value) {
            "Check not null"
        }
        set(value) {
            prefs.edit {
                val serializatedPosts = Json.encodeToString(value)
                putString(POSTS_PREFS_KEY, serializatedPosts)
            }
            data.value = value
        }

    override val data: MutableLiveData<List<Post>>

    init {
        val serializedPosts = prefs.getString(POSTS_PREFS_KEY, null)
        val posts = if (serializedPosts != null) {
            Json.decodeFromString<List<Post>>(serializedPosts)
        } else emptyList()
        data = MutableLiveData(posts)
    }

    override fun like(postId: Long) {
        posts = posts.map {
            if (it.id != postId) it
            else it.copy(
                likedByMe = !it.likedByMe,
                likes = it.likes + if(it.likedByMe) - 1 else + 1)
        }
    }

    override fun repost(postId: Long) {
        posts = posts.map {
            if (it.id != postId) it
            else it.copy(repost = it.repost + 1)
        }
    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    override fun edit(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    override fun delete(postId: Long) {
        //Получаем список постов - фильтруем - остаются, которые не являются постом, который нужно удалить
        posts = posts.filter { it.id != postId }
    }

    private fun update(post: Post) {
        posts = posts.map {
            if (it.id == post.id) post else it
        }
    }

    private fun insert(post: Post) {
        posts = listOf(
            post.copy(id = ++nextID)
        ) + posts
    }

    private companion object {
        const val GENERATED_POSTS_AMOUNT = 1000
        const val POSTS_PREFS_KEY = "posts"
        const val NEXT_ID = "nextID"
    }
}