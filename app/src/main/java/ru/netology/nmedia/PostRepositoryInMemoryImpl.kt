package ru.netology.nmedia

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class PostRepositoryInMemoryImpl : PostRepository {
    private var nextId = 1L
    private var posts = listOf(

        Post(
            id = nextId++,
            authorName = "Нетология. Университет интернет-профессий будущего",
            likedByMe = false,
            postContent = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            publishDate = "21 мая в 18:36",
            likes = 0,
            shared = 0,
            viewed = 0
        ),

        Post(
            id = nextId++,
            authorName = "Нетология. Университет интернет-профессий будущего",
            likedByMe = false,
            postContent = "XXXXXXXXXXXXXXXXXX",
            publishDate = "21 мая в 18:36",
            likes = 0,
            shared = 0,
            viewed = 0
        ),

        Post(
            id = nextId++,
            authorName = "Нетология. Университет интернет-профессий будущего",
            likedByMe = false,
            postContent = "YYYYYYYYYYYYYYYYYYY",
            publishDate = "21 мая в 18:36",
            likes = 0,
            shared = 0,
            viewed = 0
        ),

        Post(
            id = nextId++,
            authorName = "Нетология. Университет интернет-профессий будущего",
            likedByMe = false,
            postContent = "ZZZZZZZZZZZZZZZZZZZZ",
            publishDate = "21 мая в 18:36",
            likes = 0,
            shared = 0,
            viewed = 0
        ),

        Post(
            id = nextId++,
            authorName = "Нетология. Университет интернет-профессий будущего",
            likedByMe = false,
            postContent = "DDDDDDDDDDDDDDDDDDDDDDDDD",
            publishDate = "21 мая в 18:36",
            likes = 0,
            shared = 0,
            viewed = 0
        ),

        Post(
            id = nextId++,
            authorName = "Нетология. Университет интернет-профессий будущего",
            likedByMe = false,
            postContent = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV",
            publishDate = "21 мая в 18:36",
            likes = 0,
            shared = 0,
            viewed = 0
        ),

        Post(
            id = nextId++,
            authorName = "Нетология. Университет интернет-профессий будущего",
            likedByMe = false,
            postContent = "LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL",
            publishDate = "21 мая в 18:36",
            likes = 0,
            shared = 0,
            viewed = 0
        ),

        Post(
            id = nextId++,
            authorName = "Нетология. Университет интернет-профессий будущего",
            postContent = "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN",
            likedByMe = false,
            publishDate = "21 мая в 18:36",
            likes = 0,
            shared = 0,
            viewed = 0
        ),

        Post(
            id = nextId++,
            authorName = "Нетология. Университет интернет-профессий будущего",
            likedByMe = false,
            postContent = "KKKKKKKKKKKKKKKKKKKKKKKKKK",
            publishDate = "21 мая в 18:36",
            likes = 0,
            shared = 0,
            viewed = 0
        )
    )
    private val data = MutableLiveData(posts)

    override fun getAll(): LiveData<List<Post>> = data
    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1)
        }
        data.value = posts
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shared = it.shared + 1)
        }
        data.value = posts
    }

    override fun viewById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(viewed = it.viewed + 1)
        }
        data.value = posts
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(
                post.copy(
                    id = nextId++,
                    authorName = "Нетология",
                    likedByMe = false,
                    publishDate = "Только что"
                )
            ) + posts
        } else {
            posts.map {
                if (it.id != post.id) it else it.copy(postContent = post.postContent)
            }
        }
        data.value = posts
    }
}