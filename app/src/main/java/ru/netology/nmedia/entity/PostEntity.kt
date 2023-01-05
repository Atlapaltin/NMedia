package ru.netology.nmedia.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PostEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val authorName: String,
    val postContent: String,
    val publishDate: String,
    var likes: Int = 0,
    var likedByMe: Boolean = false,
    var shared: Int = 0,
    var viewed: Int = 0,
    var videoUrl: String? = null
)