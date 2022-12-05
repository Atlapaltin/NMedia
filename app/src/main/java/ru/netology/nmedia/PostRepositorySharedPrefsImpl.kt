package ru.netology.nmedia

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.netology.nmedia.Post

class PostRepositorySharedPrefsImpl(context: Context) : PostRepository {
    private val gson = Gson() //создаем класс gson, преобразующий посты в string
    //общаемся к prefs c параметрами в виде имени файла, где хранятся prefs, и типа доступа
    private val prefs = context.getSharedPreferences("repo", Context.MODE_PRIVATE)
    private val key = "posts"
    //ссылка на список постов
    private val typeToken = TypeToken.getParameterized(List::class.java, Post::class.java).type
    private var nextId = 1L
    private var posts = emptyList<Post>()
    private val data = MutableLiveData(posts)

    init {
        //считываем посты из истории последнего запуска (ключ+значение по умолчанию null)
        //и получаем посты, если они были сохранены при последнем запуске
        prefs.getString(key, null)?.let {
            //считываем текущие посты из хранилища shared preferences
            //(передаем исходные посты, тип доступа)
            posts = gson.fromJson(it, typeToken)
            //присваиваем id поста:
            // если посты есть, то берем пост с макс-м.id+1,
            //а если постов не было, то id поста будет 0+1=1
            nextId = (posts.maxOfOrNull{it.id}?:0)+1
        //указываем, что нужно выполнить, если при считывании постов вернется null:
        // т.е.выводим список постов без изменений
        } ?:run {
            posts = listOf(

                Post(
                    id = nextId++,
                    authorName = "Нетология. Университет интернет-профессий будущего",
                    likedByMe = false,
                    postContent = "Привет, тут можно посмеяться и расслабиться))) Надо иногда отвлекаться от кодинга (нет)",
                    publishDate = "21 мая в 18:36",
                    likes = 0,
                    shared = 0,
                    viewed = 0,
                    videoUrl = "https://www.youtube.com/shorts/3t5jmTHVXyA"
                ),

                Post(
                    id = nextId++,
                    authorName = "Нетология. Университет интернет-профессий будущего",
                    likedByMe = false,
                    postContent = "XXXXXXXXXXXXXXXXXX",
                    publishDate = "21 мая в 18:36",
                    likes = 0,
                    shared = 0,
                    viewed = 0
                ),

                Post(
                    id = nextId++,
                    authorName = "Нетология. Университет интернет-профессий будущего",
                    likedByMe = false,
                    postContent = "YYYYYYYYYYYYYYYYYYY",
                    publishDate = "21 мая в 18:36",
                    likes = 0,
                    shared = 0,
                    viewed = 0
                ),

                Post(
                    id = nextId++,
                    authorName = "Нетология. Университет интернет-профессий будущего",
                    likedByMe = false,
                    postContent = "ZZZZZZZZZZZZZZZZZZZZ",
                    publishDate = "21 мая в 18:36",
                    likes = 0,
                    shared = 0,
                    viewed = 0
                ),

                Post(
                    id = nextId++,
                    authorName = "Нетология. Университет интернет-профессий будущего",
                    likedByMe = false,
                    postContent = "DDDDDDDDDDDDDDDDDDDDDDDDD",
                    publishDate = "21 мая в 18:36",
                    likes = 0,
                    shared = 0,
                    viewed = 0
                ),

                Post(
                    id = nextId++,
                    authorName = "Нетология. Университет интернет-профессий будущего",
                    likedByMe = false,
                    postContent = "VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV",
                    publishDate = "21 мая в 18:36",
                    likes = 0,
                    shared = 0,
                    viewed = 0
                ),

                Post(
                    id = nextId++,
                    authorName = "Нетология. Университет интернет-профессий будущего",
                    likedByMe = false,
                    postContent = "LLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLLL",
                    publishDate = "21 мая в 18:36",
                    likes = 0,
                    shared = 0,
                    viewed = 0
                ),

                Post(
                    id = nextId++,
                    authorName = "Нетология. Университет интернет-профессий будущего",
                    postContent = "NNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN",
                    likedByMe = false,
                    publishDate = "21 мая в 18:36",
                    likes = 0,
                    shared = 0,
                    viewed = 0
                ),

                Post(
                    id = nextId++,
                    authorName = "Нетология. Университет интернет-профессий будущего",
                    likedByMe = false,
                    postContent = "KKKKKKKKKKKKKKKKKKKKKKKKKK",
                    publishDate = "21 мая в 18:36",
                    likes = 0,
                    shared = 0,
                    viewed = 0
                )
            ).reversed()
        }
        //записываем данные в livedata, чтобы данные могли обновляться во вью
        data.value = posts
    }

    //функция записи, вызываемая при каждом изменении данных
    private fun sync () {
        //берем текущий список постов и записываем в shared preferences
        prefs.edit().apply{
            //параметры: ключ key по которому записываем (тот же, по которому считывали выше) и
            //обращение к хранилищу gson через метод toJson с аргументом
            // в виде текущего списка постов posts
            putString(key,gson.toJson(posts))
            //сохраняем в репозиторий shared preferences
            apply()
        }
    }

    override fun getAll(): LiveData<List<Post>> = data

    override fun likeById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1)
        }
        data.value = posts
        sync() //запись в репозиторий
    }

    override fun shareById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(shared = it.shared + 1)
        }
        data.value = posts
        sync() //запись в репозиторий
    }

    override fun viewById(id: Long) {
        posts = posts.map {
            if (it.id != id) it else it.copy(viewed = it.viewed + 1)
        }
        data.value = posts
        sync() //запись в репозиторий
    }

    override fun removeById(id: Long) {
        posts = posts.filter { it.id != id }
        data.value = posts
        sync() //запись в репозиторий
    }

    override fun save(post: Post) {
        posts = if (post.id == 0L) {
            listOf(
                post.copy(
                    id = nextId++,
                    authorName = "Нетология",
                    likedByMe = false,
                    publishDate = "Только что"
                )
            ) + posts
        } else {
            posts.map {
                if (it.id != post.id) it else it.copy(postContent = post.postContent)
            }
        }
        data.value = posts
        sync() //запись в репозиторий
    }
}