package com.example.elementaryreading

import android.content.Context
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.elementaryreading.databinding.FragmentYesNoMenuBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class YesNoMenuFragment : Fragment() {
    private lateinit var binding: FragmentYesNoMenuBinding
    private val viewModel: YesNoMenuViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentYesNoMenuBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        if (savedInstanceState == null) {
            viewModel.playQuestion()
        }


        binding.yesButton.setOnClickListener {
            viewModel.stopPlaying()
            requireActivity().findNavController(R.id.fragmentContainerView)
                .navigate(R.id.action_yesNoMenuFragment_to_rouletteFragment)
        }
        binding.noButton.setOnClickListener {
            viewModel.stopPlaying()
            requireActivity().findNavController(R.id.fragmentContainerView)
                .navigate(R.id.action_yesNoMenuFragment_to_explanationAnimation)
        }
    }
}
