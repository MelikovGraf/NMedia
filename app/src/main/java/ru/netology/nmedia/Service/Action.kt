package ru.netology.nmedia.Service

internal enum class Action(
    val key: String,
) {
    Like("LIKE");

    companion object {
        const val KEY = "ACTION"
    }
}