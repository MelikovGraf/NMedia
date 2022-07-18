package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.databinding.FragmentFirstActivityBinding
import ru.netology.nmedia.databinding.PostEditFragmentBinding


class FirstActivity : Fragment() {

//    private val args by navArgs<PostEditFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = FragmentFirstActivityBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.button.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.button4.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.button2.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.button3.setOnClickListener {
            findNavController().navigateUp()
        }
    }.root

}