package ru.netology.nmedia.dao

import ru.netology.nmedia.datatransfer.Post

//When you use the Room library to store your app's data,
//you interact with the stored data by defining Data Access Objects (DAO).
//Each DAO includes methods that offer abstract access to your app's database.
//At compile time, Room automatically generates implementations of the DAOs that you define.
//https://developer.android.com/training/data-storage/room/accessing-data

interface PostDao {
    fun getAll(): List<Post>
    fun save(post: Post): Post
    fun likeById(id: Long)
    fun removeById(id: Long)
    fun shareById(id: Long)
    fun viewById(id: Long)
}