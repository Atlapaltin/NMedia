package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var post = Post(
        id = 1,
        authorName = "Нетология. Университет интернет-профессий будущего",
        postContent = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
        publishDate = "21 мая в 18:36",
        likes = 5099,
        likedByMe = false,
        shared = 9999,
        viewed = 1_999_999
    )
    private val data = MutableLiveData(post)

    override fun get(): LiveData<Post> = data
    override fun likeSign() {
        post = post.copy(
            likedByMe = !post.likedByMe,
            likes = if (post.likedByMe) post.likes + 1 else post.likes - 1
        )
    }

    override fun sharing() {
        post = post.copy(shared = post.shared + 1)
        data.value = post

    }

    override fun viewing() {
        post = post.copy(viewed = post.viewed + 1)
        data.value = post

    }
}