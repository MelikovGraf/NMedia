package ru.netology.NMedia.data.impl

import androidx.lifecycle.MutableLiveData
import ru.netology.NMedia.data.Post
import ru.netology.NMedia.data.PostRepository
import ru.netology.NMedia.db.PostDao

class SQLiteRepository(
    private val dao: PostDao
): PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Check not null"
        }

    override val data = MutableLiveData(dao.getAll())

    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        data.value = if(id==0L) {
            listOf(saved) + posts
        } else {
            posts.map {
                if(it.id != id) it else saved
            }
        }
    }

    override fun edit(post: Post) {
        TODO("Not yet implemented")
    }

    override fun like(id: Long) {
        dao.likedById(id)
        data.value = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if(it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
    }

    override fun repost(postId: Long) {
        TODO("Not yet implemented")
    }

    override fun delete(id: Long) {
        dao.removeById(id)
        data.value = posts.filter {it.id != id}
    }

}