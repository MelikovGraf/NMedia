package ru.netology.nmedia.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.netology.nmedia.R
import ru.netology.nmedia.ViewModel.PostViewModel
import ru.netology.nmedia.databinding.PostContentFragmentBinding
import ru.netology.nmedia.databinding.ReceptMainFragmentBinding


class ReceptMainFragment : Fragment() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.navigateToReceptEvent.observe(this) {initialContent ->
            val directions = ReceptMainFragmentDirections.toPostContentFragment(initialContent)
            findNavController().navigate(directions)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ) = ReceptMainFragmentBinding.inflate(layoutInflater, container, false).also { binding ->
        binding.groupKitchen.setOnClickListener {
            findNavController().navigate(R.id.action_receptMainFragment_to_feedFragment)
        }
        binding.groupBottom.setOnClickListener {
            findNavController().navigate(R.id.action_feedFragment_to_postContentFragment)
        }
    }.root
}