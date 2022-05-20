package ru.netology.NMedia.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.NMedia.Adapter.PostsAdapter
import ru.netology.NMedia.R
import ru.netology.NMedia.ViewModel.PostViewModel
import ru.netology.NMedia.databinding.FeedFragmentBinding
import ru.netology.NMedia.databinding.SinglePostBinding
import ru.netology.NMedia.ui.PostContentFragment.Companion.REQUEST_KEY

class FeedFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        setFragmentResultListener(requestKey = PostContentFragment.REQUEST_KEY) { requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent =
                bundle.getString(PostContentFragment.RESULT_KEY) ?: return@setFragmentResultListener
            viewModel.onSaveClicked(newPostContent)
        }

        viewModel.navigateToPostContentEvent.observe(this) { initialContent ->
            val directions = FeedFragmentDirections.toPostContentFragment(initialContent)
            findNavController().navigate(directions)
        }

        viewModel.navigateToPostContentEvent.observe(this) { initialContent ->
            val directions = FeedFragmentDirections.actionFeedFragmentToSinglePost(initialContent)
            findNavController().navigate(directions)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val adapter = PostsAdapter(viewModel)
        binding.list.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }
    }.root

    companion object {
        const val TAG = "Feed Fragment"
    }
}