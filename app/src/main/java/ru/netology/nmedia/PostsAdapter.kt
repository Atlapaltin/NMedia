package ru.netology.nmedia

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.databinding.PostCardLayoutBinding
import android.content.Intent
import android.net.Uri
import android.view.View

interface OnInteractionListener {
    fun onEdit(post: Post)
    fun onRemove(post: Post)
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onPost(post: Post)
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
    class PostDiffCallback : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }
    }

    fun bind(post: Post) {
        binding.apply {
            author.text = post.authorName
            published.text = post.publishDate
            content.text = post.postContent
            likeSign.text = Calculations.postStatistics(post.likes)
            shareSign.text = Calculations.postStatistics(post.shared)
            viewsNumberSign.text = Calculations.postStatistics(post.viewed)
            likeSign.isChecked = post.likedByMe //определяем, нажата ли иконка лайка
                                                // (она сейчас в xml -файле в виде selector)
            //слушатель нажатия лайка
            likeSign.setOnClickListener {
                interactionListener.onLike(post)
            }
            //слушатель нажатия репоста
            shareSign.setOnClickListener {
                interactionListener.onShare(post)
            }
            //слушатель нажатия кнопки меню (три верт.точки сверху поста)
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

            root.setOnClickListener {
                interactionListener.onPost(post)
            }

            //если видео-контент не пустой (прошел проверку isNullOrBlank()),
            //то запускается слушатель клика по кнопке play,
            //который при нажатии кнопки запускает интент со вью
            //и парсит содержиме ссылки в формат видео
            if (!post.videoUrl.isNullOrBlank()) {
                videoGroup.visibility = View.VISIBLE

                val videoClickListener: (View) -> Context = { view ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(post.videoUrl))
                    view.context.apply {
                        val shareIntent =
                            Intent.createChooser(intent, getString(R.string.video_play_button))
                        startActivity(shareIntent)
                    }
                }

                videoButton.setOnClickListener { view -> videoClickListener(view) } //слушатель нажатия
                videoPicture.setOnClickListener { view -> videoClickListener(view) }//превью-картинка

            } else videoGroup.visibility = View.GONE
        }
    }
}