package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.PostCardLayoutBinding

typealias OnLikeListener = (Post) -> Unit
typealias OnShareListener = (Post) -> Unit

class PostsAdapter (private val likeListener: OnLikeListener, private val shareListener: OnShareListener):
    ListAdapter<Post, PostViewHolder>(PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = PostCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, likeListener, shareListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
       holder.bind(getItem(position))
    }
}

class PostViewHolder (
    private val binding: PostCardLayoutBinding,
    private val likeListener: OnLikeListener,
    private val shareListener: OnShareListener
) : RecyclerView.ViewHolder(binding.root) {

    fun bind (post:Post) {
        binding.apply {
            author.text = post.authorName
            published.text = post.publishDate
            content.text = post.postContent
            likesNumberFigure.text = Calculations.postStatistics(post.likes)
            sharesNumberFigure.text = Calculations.postStatistics(post.shared)
            viewsNumberFigure.text = Calculations.postStatistics(post.viewed)
            likeSign.setImageResource(
                if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_baseline_favorite_border_24)
            likeSign.setOnClickListener {
                likeListener(post)
            }
            shareSign.setOnClickListener {
                shareListener(post)
            }
        }
    }
}

class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem == newItem
    }

}