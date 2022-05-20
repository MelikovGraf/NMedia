package ru.netology.NMedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import ru.netology.NMedia.databinding.SinglePostBinding

class SinglePost : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = SinglePostBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.buttonBack.setOnClickListener {
            onButtonBackClick(binding)
        }
    }.root

    private fun onButtonBackClick(binding: SinglePostBinding) {
        findNavController().popBackStack()
    }
}