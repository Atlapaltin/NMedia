package ru.netology.nmedia

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContract
import ru.netology.nmedia.databinding.ActivityNewPostBinding

class NewPostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNewPostBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //обращаемся к нопке добавления нового поста
        //и вызываем поле ввода текста поста (content (это Edit text в xml))
        binding.okButton.setOnClickListener {
            val content = binding.content.text.toString()
            //поверяем, если текст в поле ввода пустой, то вызываем метод
            //"результат ввода отменяется, иначе результат ок и тест постится"
            if (content.isEmpty()) {
                setResult(RESULT_CANCELED)
            } else {
                setResult(RESULT_OK, Intent().putExtra(Intent.EXTRA_TEXT, content)) //возвращаем
                // текст поста
            }
            finish()
        }
    }

    //класс Contract (принимает и отправляет данные поста между экранами)
// наследуется от ActivityResultContract с параметрами
// <входные данные формата Unit, выходные данные формата String?>
    object Contract : ActivityResultContract<Unit, String?>() {
        //метод отвечающий за запуск интента
        override fun createIntent(context: Context, input: Unit) =
            Intent(context, NewPostActivity::class.java)

        override fun parseResult(resultCode: Int, intent: Intent?) =
            if (resultCode == RESULT_OK) {
                intent?.getStringExtra(Intent.EXTRA_TEXT)
            } else {

                null
            }
    }

}
