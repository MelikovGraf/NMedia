package ru.netology.NMedia

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.netology.NMedia.ViewModel.PostViewModel
import ru.netology.NMedia.data.impl.PostsAdapter
import ru.netology.NMedia.databinding.ActivityMainBinding
import ru.netology.NMedia.databinding.ItemBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel::onLikeClicked,viewModel::onRepostClicked)
        binding.RecyclerView.adapter = adapter
        viewModel.data.observe(this) {posts ->
            adapter.submitList(posts)
        }
    }
}