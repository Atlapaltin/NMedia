package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.netology.nmedia.datatransfer.Post

@Entity
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorName: String,
    val postContent: String,
    val date: String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var shared: Int = 0,
    var viewed: Int = 0,
    var videoUrl: String? = null
)
//пишем функцию, которая воозвращает Post из PostEntity
{
    //передача из PostEntity в Post:
    fun toDto() = Post(
        id = id,
        authorName = authorName,
        postContent = postContent,
        publishDate = date,
        likes = likes,
        likedByMe = likedByMe,
        shared = shared,
        viewed = viewed,
        videoUrl = videoUrl,
    )

    //передача из Post в PostEntity:
    companion object {
        fun fromDto(post: Post) = PostEntity(
            id = post.id,
            authorName = post.authorName,
            postContent = post.postContent,
            date = post.publishDate,
            likes = post.likes,
            likedByMe = post.likedByMe,
            shared = post.shared,
            viewed = post.viewed,
            videoUrl = post.videoUrl,
        )
    }
}