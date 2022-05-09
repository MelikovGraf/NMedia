package ru.netology.NMedia

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import ru.netology.NMedia.Adapter.PostsAdapter
import ru.netology.NMedia.ViewModel.PostViewModel
import ru.netology.NMedia.databinding.ActivityMainBinding
import ru.netology.NMedia.util.hideKeyBoard
import ru.netology.NMedia.util.showKeyBoard

class MainActivity : AppCompatActivity() {


    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.group.visibility = View.INVISIBLE

        val adapter = PostsAdapter(viewModel)
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
            binding.group.visibility = View.INVISIBLE
        }

        binding.save.setOnClickListener {
            with(binding.content) {
                val content = binding.content.text.toString()
                viewModel.onSaveClicked(content)
            }
        }


        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.content) {
                val content = currentPost?.content
                setText(content)
                binding.group.visibility = View.VISIBLE
                binding.cancel.setOnClickListener {
                    clearFocus()
                    hideKeyBoard()
                }
                if (content != null) {
                    requestFocus()
                    showKeyBoard()
                } else {
                    clearFocus()
                    hideKeyBoard()
                }
            }
        }
    }
}