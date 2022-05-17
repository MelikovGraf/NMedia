package ru.netology.NMedia.acitivity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.launch
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.google.android.material.snackbar.Snackbar
import ru.netology.NMedia.Adapter.PostsAdapter
import ru.netology.NMedia.R
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

        val adapter = PostsAdapter(viewModel)
        binding.list.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }

        binding.fab.setOnClickListener {
            viewModel.onAddClicked()
        }

        viewModel.sharePostContent.observe(this) { postContent -> /* подготоваливаем пустой интент*/
            val intent = Intent().apply { /* Заполняем интент*/
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent) /* Полезная информация */
                type = "text/plain" /* Тип информации */
            }
            /* меню выбора приложений для репоста */
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        val postContentActivityLauncher = registerForActivityResult(
            PostContentActivity.ResultContract
        ) { postContent ->
            postContent ?: return@registerForActivityResult
            viewModel.onSaveClicked(postContent)
        }

        viewModel.navigateToPostContentEvent.observe(this) {
            postContentActivityLauncher.launch()
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

        binding.save.setOnClickListener {
            with(binding.content) {
                val content = binding.content.text.toString()
                viewModel.onSaveClicked(content)
            }
        }



//        fun videoOnClick(view: View) {
//            fun openWebPage(url: String) {
//                val webpage: Uri = Uri.parse(url)
//                val intent = Intent(Intent.ACTION_VIEW, webpage)
//                if (intent.resolveActivity(packageManager) != null) {
//                    startActivity(intent)
//                }
//            }
//            openWebPage("https://www.youtube.com/watch?v=WhWc3b3KhnY")
//        }
    }
}