package ru.netology.nmedia

import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.PostRepository
import ru.netology.nmedia.PostRepositoryInMemoryImpl

private val empty = Post(
    id = 0,
    postContent = "",
    authorName = "",
    likedByMe = false,
    publishDate = "",
    likes = 0,
    shared = 0,
    viewed = 0
)

class PostViewModel : ViewModel() {
    private val repository: PostRepository = PostRepositoryInMemoryImpl()
    val data = repository.getAll()
    val edited = MutableLiveData(empty)
    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.postContent == text) {
                return
            }
            edited.value = it.copy(postContent = text)
        }
    }

    fun cancelEdit() {
        edited.value = empty
    }
    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)
    fun editById(post: Post) {
        edited.value = post
    }

}