package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.PostViewFragmentBinding


class PostViewFragment : Fragment() {

    private val args by navArgs<PostViewFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = PostViewFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.itemViewContent.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }.root

    companion object {
        const val REQUEST_KEY = "requestKey"
        const val RESULT_KEY = "postNewContent"
    }
}