package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.Adapter.PostsAdapter
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.databinding.ItemBinding
import ru.netology.nmedia.databinding.PostViewFragmentBinding


class PostViewFragment : Fragment() {

    private val args by navArgs<PostViewFragmentArgs>()
    private val viewModel by viewModels<PostViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = PostViewFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.itemViewContent.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        val viewHolder = PostsAdapter.ViewHolder(ItemBinding.inflate(layoutInflater), viewModel)
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { true } ?: run {
                findNavController().navigateUp() // the post was deleted, close the fragment
                return@observe
            }
            viewHolder.bind(post)
        }
    }.root

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "postNewContent"
    }
}