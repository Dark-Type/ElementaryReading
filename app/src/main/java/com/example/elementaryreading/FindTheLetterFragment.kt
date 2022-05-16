package com.example.elementaryreading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.elementaryreading.databinding.FragmentFindTheLetterBinding


class FindTheLetterFragment : Fragment() {
    private lateinit var binding: FragmentFindTheLetterBinding
    private val viewModel: FindTheLetterViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentFindTheLetterBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (savedInstanceState == null) {


            if ((1 until 2).random() % 2 == 0) {
                binding.findTheLetterBackground.setImageResource(R.drawable.find_the_letter_background_2)
            } else {
                binding.findTheLetterBackground.setImageResource(R.drawable.find_the_letter_background_1)
            }
            binding.imageButton7.setOnClickListener {
                // play
            }
            binding.imageButton8.setOnClickListener {
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_findTheLetterFragment_to_settingsFragment)
            }
            binding.imageButton9.setOnClickListener {
                parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
            }
            drawRound(0)
            viewModel.stopListeningFLF()
        }
    }

    private fun drawRound(roundNumber: Int) {
        if (roundNumber == 9) {
            requireActivity().findNavController(R.id.fragmentContainerView)
                .navigate(R.id.action_findTheLetterFragment_to_victoryMenuFragment)

            return
        }

        val textView = TextView(requireContext())

        textView.apply {
            textSize = 20f
            x = (0..binding.findTheLetterBackground.width).random().toFloat()
            y = ((binding.background.height - binding.findTheLetterBackground.height) / 2..
                    (binding.background.height - binding.findTheLetterBackground.height) / 2
                    + binding.findTheLetterBackground.height).random().toFloat()
            text = HelperObject.getRandomLetter()
        }

        binding.background.addView(textView)

        binding.whiteBackground7.setOnClickListener {
            viewModel.startListeningFLF()
            if (!viewModel.isListeningFLF()) {
                viewModel.setTextFLF()
                if (viewModel.checkTheLetterFLF(textView)) {
                    drawRound(roundNumber + 1)
                }
            }
        }

    }
}
