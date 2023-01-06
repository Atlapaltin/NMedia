package ru.netology.nmedia.apprepositories

import androidx.lifecycle.map
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.datatransfer.Post
import ru.netology.nmedia.entity.PostEntity

class PostRepositoryRoomImpl(
    private val dao: PostDao
) : PostRepository {

    // вызываем трансформацию старой livedata в новую (из PostEntity в Post):
    override fun getAll() = dao.getAll().map {
            list -> list.map {it.toDto()}
    }
    //передача из Post в PostEntity:
    override fun save(post: Post) = dao.save(PostEntity.fromDto(post))

    override fun likeById(id: Long) = dao.likeById(id)

    override fun removeById(id: Long) = dao.removeById(id)

    override fun shareById(id: Long) = dao.shareById(id)

    override fun viewById(id: Long) = dao.viewById(id)
}