package ru.netology.nmedia.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ru.netology.nmedia.entity.PostEntity

//Когда мы используем библиотеку Room для хранения данных приложения,
//то при этом взаимодействуем с хранящимися данными посредством определения объектов
// доступа к данным (т.е. Data Access Objects или DAO).
//Каждый DAO включает в слебя методы, предоставляющие доступ к базе данных приложения.
//При компиляции библиотека Room обращается к реализцаии тех DAO, которые мы определили.
//https://developer.android.com/training/data-storage/room/accessing-data

@Dao
interface PostDao {
    //при помощи аннотации запроса @Query ссылаемся на LiveData, которая обновляется
    //при каждом изменении базы данных (database):

    //сортировка постов по id в порядке убывания
    @Query("SELECT * FROM PostEntity ORDER BY id DESC")
    fun getAll(): LiveData <List <PostEntity> >

    //функция добавления нового поста:
    @Insert
    fun insert(post: PostEntity)

    //функция обновления текущего поста:

    //PostEntity - имя таблицы и класса, записи которого будут храниться в таблице;
    // обновляем запись, у которой id соответствует значению параметра id
    @Query ("UPDATE PostEntity SET postContent = :postContent WHERE id = :id")
    fun updateContentById(id: Long, postContent: String)

    fun save(post: PostEntity) = if (post.id == 0L) insert(post) else updateContentById(post.id, post.postContent)


    @Query(
        """
           UPDATE PostEntity SET
               likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = :id;
        """
    )
    fun likeById(id: Long)

    @Query("DELETE FROM PostEntity WHERE id = :id")
    fun removeById(id: Long)

    @Query(
        """
        UPDATE PostEntity SET
        shared = shared + 1
        WHERE id = :id
        """
    )
    fun shareById(id: Long)

    @Query(
        """
        UPDATE PostEntity SET
        viewed = viewed + 1
        WHERE id = :id
        """
    )
    fun viewById(id: Long)
}