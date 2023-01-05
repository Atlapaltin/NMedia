package ru.netology.nmedia.dao

import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.datatransfer.Post

//Это реализация фйла DAO (т.е. нашего "PostDao", в котором пеечислены методы)
//т.е. в PostDao методы перечислены, а тут они реализованы

//ссылаемся на базу данных и таблицу в ней:
class PostDaoImpl(private val db: SQLiteDatabase) : PostDao {
    companion object {
        val DDL = """
        CREATE TABLE ${PostColumns.TABLE} (
            ${PostColumns.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${PostColumns.COLUMN_AUTHORNAME} TEXT NOT NULL,
            ${PostColumns.COLUMN_POSTCONTENT} TEXT NOT NULL,
            ${PostColumns.COLUMN_PUBLISHDATE} TEXT NOT NULL,
            ${PostColumns.COLUMN_LIKED_BY_ME} BOOLEAN NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_LIKES} INTEGER NOT NULL DEFAULT 0,
            ${PostColumns.COLUMN_SHARED} INTEGER NOT NULL DEFAULT 0
            ${PostColumns.COLUMN_VIEWED} INTEGER NOT NULL DEFAULT 0
            
        );
        """.trimIndent()
    }

    object PostColumns {
        const val TABLE = "posts"
        const val COLUMN_ID = "id"
        const val COLUMN_AUTHORNAME = "authorName"
        const val COLUMN_POSTCONTENT = "postContent"
        const val COLUMN_PUBLISHDATE = "publishDate"
        const val COLUMN_LIKED_BY_ME = "likedByMe"
        const val COLUMN_LIKES = "likesNumber"
        const val COLUMN_SHARED = "sharesNumber"
        const val COLUMN_VIEWED = "viewsNumber"

        val ALL_COLUMNS = arrayOf(
            COLUMN_ID,
            COLUMN_AUTHORNAME,
            COLUMN_POSTCONTENT,
            COLUMN_PUBLISHDATE,
            COLUMN_LIKED_BY_ME,
            COLUMN_LIKES,
            COLUMN_SHARED,
            COLUMN_VIEWED
        )
    }

    //описываем реализацию методов, перечисленных в DAO:
    override fun getAll(): List<Post> {
        val posts = mutableListOf<Post>()
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            null,
            null,
            null,
            null,
            "${PostColumns.COLUMN_ID} DESC"
        ).use {
            while (it.moveToNext()) {
                posts.add(map(it))
            }
        }
        return posts
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            // TODO: remove hardcoded values
            put(PostColumns.COLUMN_AUTHORNAME, "Me")
            put(PostColumns.COLUMN_POSTCONTENT, post.postContent)
            put(PostColumns.COLUMN_PUBLISHDATE, "now")
        }
        val id = if (post.id != 0L) {
            db.update(
                PostColumns.TABLE,
                values,
                "${PostColumns.COLUMN_ID} = ?",
                arrayOf(post.id.toString()),
            )
            post.id
        } else {
            db.insert(PostColumns.TABLE, null, values)
        }
        db.query(
            PostColumns.TABLE,
            PostColumns.ALL_COLUMNS,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null,
        ).use {
            it.moveToNext()
            return map(it)
        }
    }

    override fun likeById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               likesNumber = likesNumber + CASE WHEN likedByMe THEN -1 ELSE 1 END,
               likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun removeById(id: Long) {
        db.delete(
            PostColumns.TABLE,
            "${PostColumns.COLUMN_ID} = ?",
            arrayOf(id.toString())
        )
    }

    override fun shareById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               sharesNumber = sharesNumber + CASE WHEN shared THEN 1 ELSE -1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    override fun viewById(id: Long) {
        db.execSQL(
            """
           UPDATE posts SET
               viewsNumber = viewsNumber + CASE WHEN viewed THEN 1 ELSE -1 END
           WHERE id = ?;
        """.trimIndent(), arrayOf(id)
        )
    }

    private fun map(cursor: Cursor): Post {
        with(cursor) {
            return Post(
                id = getLong(getColumnIndexOrThrow(PostColumns.COLUMN_ID)),
                authorName = getString(getColumnIndexOrThrow(PostColumns.COLUMN_AUTHORNAME)),
                postContent = getString(getColumnIndexOrThrow(PostColumns.COLUMN_POSTCONTENT)),
                publishDate = getString(getColumnIndexOrThrow(PostColumns.COLUMN_PUBLISHDATE)),
                likedByMe = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKED_BY_ME)) != 0,
                likes = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),
                shared = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),
                viewed = getInt(getColumnIndexOrThrow(PostColumns.COLUMN_LIKES)),

            )
        }
    }
}
