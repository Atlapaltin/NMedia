package ru.netology.nmedia

import androidx.lifecycle.LiveData
import ru.netology.nmedia.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun viewById(id: Long)
}