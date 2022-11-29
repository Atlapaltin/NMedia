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
            postContent = "Привет, тут можно посмеяться и расслабиться))) Надо иногда отвлекаться от кодинга (нет)",
            publishDate = "21 мая в 18:36",
            likes = 0,
            shared = 0,
            viewed = 0,
            videoUrl = "https://www.youtube.com/shorts/3t5jmTHVXyA"
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