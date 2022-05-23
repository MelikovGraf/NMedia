package ru.netology.nmedia.data.impl

import androidx.lifecycle.map
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel

class PostRepositoryImpl(
    private val dao: PostDao,
) : PostRepository {

    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }

    override fun save(post: Post) {
        if(post.id == PostRepository.NEW_POST_ID) dao.insert(post.toEntity()) else
            dao.updateContentById(post.id, post.content)
    }

    override fun delete(id: Long) = dao.removeById(id)

    override fun like(id: Long) = dao.likeById(id)

    override fun edit(post: Post) {
        TODO("Not yet implemented")
    }

    override fun repost(postId: Long) {
        TODO("Not yet implemented")
    }
}