package ru.netology.NMedia.db

import ru.netology.NMedia.data.Post
import android.database.Cursor

fun Cursor.toPost() = Post(
    id = getLong(getColumnIndexOrThrow(PostsTable.Column.ID.columnName)),
    author = getString(getColumnIndexOrThrow(PostsTable.Column.AUTHOR.columnName)),
    content = getString(getColumnIndexOrThrow(PostsTable.Column.CONTENT.columnName)),
    published = getString(getColumnIndexOrThrow(PostsTable.Column.PUBLISHED.columnName)),
    likes = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKES.columnName)),
    likedByMe = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKED_BY_ME.columnName)) != 0
)