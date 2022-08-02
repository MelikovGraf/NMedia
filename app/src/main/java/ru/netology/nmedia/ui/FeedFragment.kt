package ru.netology.nmedia.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.result.launch
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.Adapter.PostsAdapter
import ru.netology.nmedia.R
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.databinding.FeedFragmentBinding
import ru.netology.nmedia.ui.intent.intentEditContentActivity

class FeedFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val intentContentLauncher = registerForActivityResult(
//            intentEditContentActivity.ResultContract) { postContent ->
//            postContent ?: return@registerForActivityResult
//            viewModel.onSaveClicked(postContent)
//
//        }
//        viewModel.navigateToPostContentEvent.observe(this) {
//            intentContentLauncher.launch()
//        }

        viewModel.playVideoEvent.observe(this) { url ->
            val intent = Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=WhWc3b3KhnY"))
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

        setFragmentResultListener(requestKey = PostEditFragment.REQUEST_KE) { requestKey, bundle ->
            if (requestKey != PostEditFragment.REQUEST_KE) return@setFragmentResultListener
            val newPostContent =
                bundle.getString(PostEditFragment.RESULT_KE) ?: return@setFragmentResultListener
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