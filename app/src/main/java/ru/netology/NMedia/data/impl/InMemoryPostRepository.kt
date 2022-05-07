package ru.netology.NMedia.data

import androidx.lifecycle.MutableLiveData

class InMemoryPostRepository : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Check not null"
        }

    override val data = MutableLiveData(
        List(10) { index ->
            Post(
                id = index + 1L,
                author = "Graf Melikov",
                content = "$index. Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
                date = "30 April 18:36",
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
}