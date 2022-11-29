package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel: PostViewModel by viewModels()
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {viewModel.editById(post)}
            override fun onRemove(post: Post) {viewModel.removeById(post.id)}
            override fun onLike(post: Post) {viewModel.likeById(post.id)}
            override fun onShare(post: Post) {
                //Создаем Intent. Это наше намерение выполнить команду на запуск активити,
                // заложенных в него действий и передачу данных между разными активити
                val intent = Intent().apply{
                    action = Intent.ACTION_SEND //отправка данных (репост)
                    putExtra(Intent.EXTRA_TEXT, post.postContent) //ссылка на текст поста
                    type="text/plain" //тип отправляемых данных (т.е.текста поста)
                                      //можно указать text/* для текста любого формата
                                      //или */* для любого формата данных
                }
                //создаем диалоговое окно выбора действий для юзера ("Поделиться")
                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent) //запуск активити
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            val newPost = adapter.itemCount < posts.size
            //если длина текущ.списка постов меньше длины
            // нового списка, то происходит промотка по методу ниже
            //(т.е. если на адаптере количество элементов itemCount
            // меньше новой длины списка постов, то это вызывается метод промотки ниже)
            adapter.submitList(posts) {
                if(newPost) {
                    binding.list.smoothScrollToPosition(0) // перематывает ленту до последнего
                }                                              // добавленного поста (т.е. до нулевого)
            }
        }

        val activityLauncher = registerForActivityResult(NewPostActivity.Contract) {text ->
            //если text null, то выйти, иначе
            // вносим введенный текст в добавляемый пост и
            // сохраняем пост через viewModel
            text?:return@registerForActivityResult
            viewModel.changeContentAndSave(text.toString())
        }

        viewModel.edited.observe (this) {post ->
            if (post.id == 0L) {
                return@observe
            }
            //редактирование поста
            activityLauncher.launch(post.postContent)

        }
        binding.add.setOnClickListener {
            //метод, запускающий контракт (т.е. интент+активити с кнопкой добавления поста)
           activityLauncher.launch(null)
        }
    }
}
