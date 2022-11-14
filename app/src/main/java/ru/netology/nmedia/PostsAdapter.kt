package ru.netology.nmedia

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.PostCardLayoutBinding

interface OnInteractionListener {
    fun onEdit(post: Post)
    fun onRemove(post: Post)
    fun onLike(post: Post)
    fun onShare(post: Post)
}

class PostsAdapter (
    private val interactionListener: OnInteractionListener):
    ListAdapter<Post, PostViewHolder>(PostViewHolder.PostDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = PostCardLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, interactionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
       holder.bind(getItem(position))
    }
}

class PostViewHolder (
    private val binding: PostCardLayoutBinding,
    private val interactionListener: OnInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(post: Post) {
        binding.apply {
            author.text = post.authorName
            published.text = post.publishDate
            content.text = post.postContent
            //likesNumberFigure.text = Calculations.postStatistics(post.likes)
            likeSign.text = Calculations.postStatistics(post.likes)
            shareSign.text = Calculations.postStatistics(post.shared)
            viewsNumberSign.text = Calculations.postStatistics(post.viewed)
           //sharesNumberFigure.text = Calculations.postStatistics(post.shared)
            // viewsNumberFigure.text = Calculations.postStatistics(post.viewed)
            likeSign.isChecked = post.likedByMe //определяем, нажата ли иконка лайка
                                                // (он сейчас в xml -файле в виде selector)

            //likesNumberFigure.text = post.likes.toString()

            // likeSign.setImageResource(
           //     if (post.likedByMe) R.drawable.ic_liked_24 else R.drawable.ic_baseline_favorite_border_24
          //  )
            likeSign.setOnClickListener {
                interactionListener.onLike(post)
            }
            shareSign.setOnClickListener {
                interactionListener.onShare(post)
            }
            menu.setOnClickListener {
                PopupMenu(it.context, it).apply {
                    inflate(R.menu.options_post)
                    setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.remove -> {
                                interactionListener.onRemove(post)
                                true
                            }
                            R.id.edit -> {
                                interactionListener.onEdit(post)
                                true
                            }
                            else -> false
                        }
                    }
                }.show()
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
}