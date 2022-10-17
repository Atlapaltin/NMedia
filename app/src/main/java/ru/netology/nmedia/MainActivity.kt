package ru.netology.nmedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.PostViewModel
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

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
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

            }
            binding.shareSign.setOnClickListener {
                post.shared++

            }
        }
    }
}


