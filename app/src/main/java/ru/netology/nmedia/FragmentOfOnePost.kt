package ru.netology.nmedia

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.databinding.FragmentOfOnePostBinding


class FragmentOfOnePost : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentOfOnePostBinding.inflate(inflater, container, false)
        val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)
        val viewHolder = PostViewHolder (binding.onePostFragment, object: OnInteractionListener{

            override fun onEdit(post: Post) {viewModel.editById(post)}
            override fun onRemove(post: Post) {viewModel.removeById(post.id)}
            override fun onLike(post: Post) {viewModel.likeById(post.id)}
            override fun onShare(post: Post) {
                //Создаем Intent. Это наше намерение выполнить команду на запуск активити,
                // заложенных в него действий и передачу данных между разными активити
                val intent = Intent().apply{
                    action = Intent.ACTION_SEND //отправка данных (репост)
                    putExtra(Intent.EXTRA_TEXT, post.postContent) //ссылка на текст поста
                    type="text/plain" //тип отправляемых данных (т.е.текста поста)
                    //можно указать text/* для текста любого формата
                    //или */* для любого формата данных
                }
                //создаем диалоговое окно выбора действий для юзера ("Поделиться")
                val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
                startActivity(shareIntent) //запуск активити
            }
        })

      //начинаем наблюдать за изменениям в ленте постов
        viewModel.data.observe(viewLifecycleOwner) {
            posts -> val post = posts.find {it.id == 1L} ?: run {
                findNavController().navigateUp()
            return@observe
        }
        }

        //текст отображаемого поста
        viewHolder.bind (Post(
            id = 1,
            authorName = "Ksenia",
            postContent = """Фрагмент поста про 汉语""",
            publishDate = "18.12.2022",
            likedByMe = false,
            shared = 0,
            likes = 0,
            viewed = 0,
            videoUrl = null
        ))

        return binding.root

    }

    companion object {
        var Bundle.textArg by StringArg
    }
}
