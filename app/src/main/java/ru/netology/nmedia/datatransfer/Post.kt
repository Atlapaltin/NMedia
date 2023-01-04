package ru.netology.nmedia.datatransfer

data class Post(
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