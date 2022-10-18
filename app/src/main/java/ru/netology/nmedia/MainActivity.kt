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

        val viewModel: PostViewModel by viewModels()
        viewModel.data.observe(this) { post ->
            with(binding) {
                author.text = post.authorName
                published.text = post.publishDate
                content.text = post.postContent
                likesNumberFigure.text = Calculations.postStatistics(post.likes)
                sharesNumberFigure.text = Calculations.postStatistics(post.shared)
                viewsNumberFigure.text = Calculations.postStatistics(post.viewed)
                likeSign.setImageResource(
                    if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_baseline_favorite_border_24
                )
            }

            binding.likeSign.setOnClickListener {
                viewModel.likeSign()
            }

            binding.shareSign.setOnClickListener {
                viewModel.sharing()
            }
        }
    }
}


