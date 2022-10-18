package ru.netology.nmedia

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.PostRepository
import ru.netology.nmedia.PostRepositoryInMemoryImpl

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.get()
    fun likeSign() = repository.likeSign()
    fun sharing() = repository.sharing()
}