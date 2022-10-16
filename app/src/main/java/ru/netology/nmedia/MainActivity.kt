package ru.netology.nmedia

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import ru.netology.nmedia.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        @DrawableRes
        fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_liked_24 else R.drawable.ic_baseline_favorite_border_24

        val post = Post(
            id = 1,
            authorName = "Нетология. Университет интернет-профессий будущего",
            postContent = "Привет, это новая Нетология! Когда-то Нетология начиналась с интенсивов по онлайн-маркетингу. Затем появились курсы по дизайну, разработке, аналитике и управлению. Мы растём сами и помогаем расти студентам: от новичков до уверенных профессионалов. Но самое важное остаётся с нами: мы верим, что в каждом уже есть сила, которая заставляет хотеть больше, целиться выше, бежать быстрее. Наша миссия — помочь встать на путь роста и начать цепочку перемен → http://netolo.gy/fyb",
            publishDate = "21 мая в 18:36",
            likes = 5099,
            likedByMe = false,
            shared = 9999,
            viewed = 1_999_999
        )

        with(binding) {
            author.text = post.authorName
            published.text = post.publishDate
            content.text = post.postContent
            likesNumberFigure.text = Calculations.postStatistics(post.likes)
            sharesNumberFigure.text = Calculations.postStatistics(post.shared)
            viewsNumberFigure.text = Calculations.postStatistics(post.viewed)
        }

            binding.likeSign.setOnClickListener {
            post.likedByMe = !post.likedByMe
            binding.likeSign.setImageResource(getLikeIconResId(post.likedByMe))
            if (post.likedByMe) post.likes++ else post.likes--
            //binding.likesNumberFigure.text = Calculations.postStatistics(post.likes)
            //я сомневалась, нужно ли тут упоминать функцию...в принципе все работает
            // и при наличиии только лишь вызова функции выше в блоке with(binding)
        }
        binding.shareSign.setOnClickListener {
            post.shared++
            //binding.sharesNumberFigure.text = Calculations.postStatistics(post.shared)
            //я сомневалась, нужно ли тут упоминать функцию...в принципе все работает
            // и при наличиии только лишь вызова функции выше в блоке with(binding)
        }
    }
}


