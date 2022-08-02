package ru.netology.nmedia.Adapter

import ru.netology.nmedia.data.Post

interface PostInteractionListener {

    fun onLikeClicked(post:Post)
    fun onRemoveClicked(post:Post)
    fun onEditClicked(post:Post)
    fun onShareClicked(post:Post)
    fun onViewClicked(post: Post)
    fun onPlayVideoClicked(post: Post)
}