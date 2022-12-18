package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_INDEFINITE
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.NewPostFragment.Companion.textArg
import ru.netology.nmedia.databinding.ActivityAppBinding

class AppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //обращаемся к данным интента через safe call (на случай, если там null)
        intent?.let {
            //если action не равен Intent.ACTION_SEND, то return@let (let нужен для обращения
            //из лямбда-выражения)
            if (it.action != Intent.ACTION_SEND) {
                return@let
            }

            val text = it.getStringExtra(Intent.EXTRA_TEXT)
            if (text.isNullOrBlank()) {
                Snackbar.make(
                    binding.root,
                    R.string.error_empty_content,
                    LENGTH_INDEFINITE
                )
                    //устанавливаем кнопку для пользователя (что делать)
                    .setAction(android.R.string.ok) {
                        finish() //закрываем активити
                    }
                    .show() //вызывает метод отображения сообщения о пустом содержимом
                return@let
            }
            val navHostFragment = supportFragmentManager.findFragmentById(R.id.container)
                    as NavHostFragment
            navHostFragment.navController.navigate(
                R.id.action_feedFragment_to_newPostFragment,
            Bundle().apply {textArg = text}
            )
        }
    }
}
