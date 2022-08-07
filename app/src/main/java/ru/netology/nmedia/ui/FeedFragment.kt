package ru.netology.nmedia.ui

import android.app.SearchManager
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.Adapter.PostsAdapter
import ru.netology.nmedia.R
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.databinding.FeedFragmentBinding

class FeedFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.playVideoEvent.observe(this) {
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=WhWc3b3KhnY"))
            startActivity(intent)
        }

        viewModel.searchEvent.observe(this) {
            val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                putExtra(SearchManager.QUERY, "Как собрать нормальное Android приложение")
            }
            startActivity(intent)
        }

        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        viewModel.navigateToPostContentEvent.observe(this) { initialContent ->
            val directions = FeedFragmentDirections.toPostContentFragment(initialContent)
            findNavController().navigate(directions)
        }

        viewModel.navigateToPostContentEvent.observe(this) { initialContent ->
            val directions = FeedFragmentDirections.toPostContentFragment(initialContent)
            findNavController().navigate(directions)
        }

        setFragmentResultListener(requestKey = PostContentFragment.REQUEST_KEY_SAVE) { requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY_SAVE) return@setFragmentResultListener
            val newPostContent =
                bundle.getString(PostContentFragment.RESULT_KEY_SAVE)
                    ?: return@setFragmentResultListener
            viewModel.onSaveClicked(newPostContent)
        }

        setFragmentResultListener(requestKey = PostContentFragment.REQUEST_KEY_EDIT) { requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY_EDIT) return@setFragmentResultListener
            val newPostContent =
                bundle.getString(PostContentFragment.RESULT_KEY_EDIT)
                    ?: return@setFragmentResultListener
            viewModel.onEditsClicked(newPostContent)
        }

        viewModel.navigateToPostViewEvent.observe(this) {
            val directions = FeedFragmentDirections.toPostViewFragment(this.id.toLong())
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
}