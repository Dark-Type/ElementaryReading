package com.example.elementaryreading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.elementaryreading.databinding.FragmentGuessTheLetterBinding


class GuessTheLetterFragment : Fragment() {
    private lateinit var binding: FragmentGuessTheLetterBinding
    private val viewModel: GuessTheLetterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentGuessTheLetterBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            var whichLineForY = 0
            var whichLineForX = 0
            binding.imageButton7.setOnClickListener {
                //play sound button
            }
            binding.imageButton8.setOnClickListener {
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_guessTheLetterFragment_to_settingsFragment)
            }
            binding.imageButton9.setOnClickListener {
                parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
            }
            for (i in 0..7) {
                if (whichLineForX == 3) {
                    whichLineForX = 0
                }
                if (i == 3) {
                    whichLineForY = 2
                }
                val viewToAdd = TextView(context)
                viewToAdd.tag = "view$i"

                viewToAdd.layoutParams = ConstraintLayout.LayoutParams(
                    0,
                    0
                ).apply {
                    leftToRight = resources.getIdentifier(
                        "guessTheLetterGuideline$whichLineForX",
                        "id",
                        requireActivity().packageName
                    )
                    rightToLeft = resources.getIdentifier(
                        "guessTheLetterGuideline${whichLineForX+1}",
                        "id",
                        requireActivity().packageName
                    )
                    topToBottom = resources.getIdentifier(
                        "guessTheLetterHorizontalGuideline$whichLineForY",
                        "id",
                        requireActivity().packageName

                    )
                    bottomToTop = resources.getIdentifier(
                        "guessTheLetterHorizontalGuideline${whichLineForY+1}",
                        "id",
                        requireActivity().packageName
                    )
binding.mainBackground.addView(viewToAdd)
                    whichLineForX++


                }
            }

//            for (i in 0..9) {
//                val currentNumber = viewModel.getRandomisedNumber()
//                val currentLetter = viewModel.getRandomLetterFromList()
//                for (j in 0..7) {
//                    if (view.tag.equals("view$currentNumber")) {
//
//                        binding.mainBackground.findViewWithTag<TextView>("View$j").text =
//                            currentLetter.toString()
//                        binding.mainBackground.findViewWithTag<TextView>("View$j")
//                            .setOnClickListener {
//                            }
//                    } else {
//                        binding.mainBackground.findViewWithTag<TextView>("View$j").text =
//                            viewModel.getRandomLetterFromList().toString()
//                    }
//                }
//            }
//            drawRound(0)
//            requireActivity().findNavController(R.id.fragmentContainerView)
//                .navigate(R.id.action_guessTheLetterFragment_to_victoryMenuFragment)


        }
    }

    private fun drawRound(roundNumber: Int) {
        if (roundNumber == 9) {
            return
        }
        val currentNumber = viewModel.getRandomisedNumber()
        val currentLetter = viewModel.getRandomLetterFromList()
        view?.let {
            for (j in 0..7) {
                if (it.tag.equals("view$currentNumber")) {
                    binding.mainBackground.findViewWithTag<TextView>("View$j").text =
                        currentLetter.toString()
                    binding.mainBackground.findViewWithTag<TextView>("View$j")
                        .setOnClickListener {
                            drawRound(roundNumber + 1)
                        }
                } else {
                    binding.mainBackground.findViewWithTag<TextView>("View$j").text =
                        viewModel.getRandomLetterFromList().toString()
                }

            }
        }
    }
}