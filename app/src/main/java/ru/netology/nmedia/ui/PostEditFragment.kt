package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.PostEditFragmentBinding


class PostEditFragment : Fragment() {

    private val args by navArgs<PostEditFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = PostEditFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.edit.setText(args.initialContent)
        binding.edit.requestFocus()
        binding.ok.setOnClickListener {
            onOkButtonClick(binding)
        }
    }.root

    private fun onOkButtonClick(binding: PostEditFragmentBinding) {
        val text = binding.edit.text
        if (!text.isNullOrBlank()) {
            val resultBundle = Bundle(1)
            resultBundle.putString(RESULT_KE, text.toString())
            setFragmentResult(REQUEST_KE, resultBundle)
        }
        findNavController().popBackStack()
    }

    companion object {
        const val REQUEST_KE = "requestKe"
        const val RESULT_KE = "postNewConten"
    }
}