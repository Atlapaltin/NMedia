package ru.netology.nmedia

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

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

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository = PostRepositoryFilesImpl(application)
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun changeContentAndSave(content: String) {
        edited.value?.let {
            //проверяем, что текст поста отредактирован
            //и если он отредактирован, то копируем его и сохраняем
            val text = content.trim()
            if (it.postContent == text) {
                //return
                repository.save(it.copy(postContent = text))
            }
            edited.value?.let {
                repository.save(it.copy(postContent = text))
            }
            edited.value = empty
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