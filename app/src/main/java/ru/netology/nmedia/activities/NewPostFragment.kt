package ru.netology.nmedia.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.tools.AndroidUtils
import ru.netology.nmedia.viewtools.PostViewModel
import ru.netology.nmedia.tools.StringArg
import ru.netology.nmedia.databinding.FragmentNewPostBinding

class NewPostFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentNewPostBinding.inflate(inflater, container, false)
        arguments?.textArg?.let {
            binding.content.setText(it)
        } //можно также записать как: arguments?.textArg?.let(binding.content::setText)

        val viewModel by viewModels<PostViewModel>(ownerProducer = ::requireParentFragment)

        binding.content.requestFocus()

        //обращаемся к кнопке добавления нового поста
        //и вызываем поле ввода текста поста (content (это Edit text в xml))
        binding.okButton.setOnClickListener {
            val text = binding.content.text.toString()
            if (text.isNotBlank()) {
                viewModel.changeContentAndSave(text)
            }
            AndroidUtils.hideKeyboard(requireView())
            findNavController().navigateUp()
        }
        return binding.root
    }

    companion object {
        var Bundle.textArg by StringArg
    }
}
