package ru.netology.nmedia

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun get(): LiveData<Post>
    fun like()
}