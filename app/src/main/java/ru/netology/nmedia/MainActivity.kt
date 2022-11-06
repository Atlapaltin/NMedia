package ru.netology.nmedia

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.PostViewModel
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.post_card_layout.*
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.databinding.ActivityMainBinding.inflate
import ru.netology.nmedia.databinding.PostCardLayoutBinding
import ru.netology.nmedia.databinding.PostCardLayoutBinding.inflate

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
            override fun onShare(post: Post) {viewModel.shareById(post.id)}
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

        viewModel.edited.observe (this) {post ->
            if (post.id == 0L) {
                return@observe
            }
            with(binding.content) {
                requestFocus()
                setText(post.postContent)
            }
            binding.oldText.text = post.postContent
            binding.editCancelGroup.visibility = View.VISIBLE
        }
        binding.save.setOnClickListener {
            //метод, вызывающий сообщение о том, что пост не может быть пустым
            with(binding.content) {
               val text = text.toString()
                if(text.isBlank()) {
                    Toast.makeText(
                        this@MainActivity,
                        R.string.description_post_empty_post, //ссылаемся на текст сообщения
                        Toast.LENGTH_SHORT // указыает на продолжительность показа сообщения
                    ).show()
                    return@setOnClickListener
                }

                viewModel.changeContent(text)
                viewModel.save()
                setText("")
                clearFocus() //удаляем курсор из строки ввода после сохранения поста
                AndroidUtils.hideKeyboard(it) //прячем клаву после вода и сохранения поста
                binding.editCancelGroup.visibility = View.GONE
            }
        }
        binding.cancelEdit.setOnClickListener {
            with(binding.content) {
                viewModel.cancelEdit()

                setText("")
                clearFocus()
                AndroidUtils.hideKeyboard(this)
                binding.editCancelGroup.visibility = View.GONE
            }
            binding.oldText.text = ""
        }
    }
}
