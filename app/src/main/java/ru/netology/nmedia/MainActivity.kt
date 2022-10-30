package ru.netology.nmedia

import android.os.Bundle
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
        val adapter = PostsAdapter ( likeListener = {
            viewModel.likeById(it.id)}, shareListener = {viewModel.shareById(it.id)})
        binding.list.adapter = adapter

        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)


                }
            }
        }
