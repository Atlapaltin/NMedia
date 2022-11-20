package ru.netology.nmedia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.databinding.ActivityIntentHandlerBinding

class IntentHandlerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityIntentHandlerBinding.inflate(layoutInflater)
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
                    Snackbar.LENGTH_INDEFINITE
                )
                    //устанавливаем кнопку для пользователя (что делать)
                    .setAction(android.R.string.ok) {
                        finish() //закрываем активити
                    }
                    .show() //вызывает метод отображения сообщения о пустом содержимом
                return@let
            }
            //если текст не пустой (написать действие на этот случай)
            Toast.makeText(this, text, Toast.LENGTH_LONG).show()
        }
    }
}
