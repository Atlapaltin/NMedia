package ru.netology.nmedia.activities

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.*
import ru.netology.nmedia.activities.FragmentOfOnePost.Companion.idArg
import ru.netology.nmedia.activities.FragmentOfOnePost.Companion.textArg
import ru.netology.nmedia.adapters.OnInteractionListener
import ru.netology.nmedia.adapters.PostsAdapter
import ru.netology.nmedia.databinding.FragmentFeedBinding
import ru.netology.nmedia.datatransfer.Post
import ru.netology.nmedia.viewtools.PostViewModel

class FeedFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentFeedBinding.inflate(inflater, container, false)

        val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)
        val adapter = PostsAdapter(object : OnInteractionListener {
            override fun onEdit(post: Post) {
                viewModel.editById(post)
            }

            override fun onRemove(post: Post) {
                viewModel.removeById(post.id)
            }

            override fun onLike(post: Post) {
                viewModel.likeById(post.id)
            }

            override fun onShare(post: Post) {
                //Создаем Intent. Это наше намерение выполнить команду на запуск активити,
                // заложенных в него действий и передачу данных между разными активити
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND //отправка данных (репост)
                    putExtra(Intent.EXTRA_TEXT, post.postContent) //ссылка на текст поста
                    type = "text/plain" //тип отправляемых данных (т.е.текста поста)
                    //можно указать text/* для текста любого формата
                    //или */* для любого формата данных
                }
                //создаем диалоговое окно выбора действий для юзера ("Поделиться")
                val shareIntent =
                    Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent) //запуск активити
            }

            override fun onPost(post: Post) {
                findNavController().navigate(
                    R.id.action_feedFragment_to_fragmentOfOnePost,
                    Bundle().apply { idArg = post.id })
            }
        })

        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val newPost = adapter.itemCount < posts.size
            //если длина текущ.списка постов меньше длины
            // нового списка, то происходит промотка по методу ниже
            //(т.е. если на адаптере количество элементов itemCount
            // меньше новой длины списка постов, то это вызывается метод промотки ниже)
            adapter.submitList(posts) {
                if (newPost) {
                    binding.list.smoothScrollToPosition(0) // перематывает ленту до последнего
                }                                              // добавленного поста (т.е. до нулевого)
            }
        }

        viewModel.edited.observe(viewLifecycleOwner) { post ->
            if (post.id == 0L) {
                return@observe
            }
            findNavController().navigate(
                R.id.action_feedFragment_to_newPostFragment,
                Bundle().apply { textArg = post.postContent })
        }
        binding.add.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_newPostFragment)
        }
        return binding.root
    }
}
