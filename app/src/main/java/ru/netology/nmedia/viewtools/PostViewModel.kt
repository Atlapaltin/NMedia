package ru.netology.nmedia.viewtools

import android.app.Application
import androidx.lifecycle.*
import ru.netology.nmedia.database.AppDb
import ru.netology.nmedia.datatransfer.Post
import ru.netology.nmedia.apprepositories.*

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
    private val repository: PostRepository = PostRepositoryRoomImpl(
        AppDb.getInstance(application).postDao
    )
    val data = repository.getAll()
    val edited = MutableLiveData(empty)

    fun save() {
        edited.value?.let {
            repository.save(it)
        }
        edited.value = empty
    }

    fun editById(post: Post) {
        edited.value = empty
    }

    fun changeContent(postContent:String){
        val text = postContent.trim()
        if (edited.value?.postContent == text) {
            return
        }
        edited.value = edited.value?.copy(postContent = text)
    }

    fun likeById(id: Long) = repository.likeById(id)
    fun shareById(id: Long) = repository.shareById(id)
    fun removeById(id: Long) = repository.removeById(id)

}