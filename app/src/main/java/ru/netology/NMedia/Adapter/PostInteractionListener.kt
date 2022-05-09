package ru.netology.NMedia.Adapter

import ru.netology.NMedia.data.Post

interface PostInteractionListener {

    fun onLikeClicked(post:Post)
    fun onRepostClicked(post:Post)
    fun onRemoveClicked(post:Post)
    fun onEditClicked(post:Post)
}