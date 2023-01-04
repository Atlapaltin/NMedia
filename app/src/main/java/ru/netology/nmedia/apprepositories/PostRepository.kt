package ru.netology.nmedia.apprepositories

import androidx.lifecycle.LiveData
import ru.netology.nmedia.datatransfer.Post

interface PostRepository {
    fun getAll(): LiveData<List<Post>>
    fun likeById(id: Long)
    fun shareById(id: Long)
    fun viewById(id: Long)
    fun removeById(id: Long)
    fun save(post: Post)
}