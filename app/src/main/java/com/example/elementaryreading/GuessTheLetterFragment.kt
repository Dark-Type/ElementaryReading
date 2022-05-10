package com.example.elementaryreading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewmodel.compose.viewModel
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
            var whichLineForY: Int = 0
            var whichLineForX: Int = 0
            binding.imageButton7.setOnClickListener(View.OnClickListener {
                //play sound button
            })
            binding.imageButton8.setOnClickListener(View.OnClickListener {
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_guessTheLetterFragment_to_settingsFragment)
            })
            binding.imageButton9.setOnClickListener(View.OnClickListener {
                parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
            })
            for (i in 0..7) {
                if (whichLineForX == 3) {
                    whichLineForX = 0
                }
                if (i == 3) {
                    whichLineForY = 2
                }
                val viewToAdd = TextView(context)
                viewToAdd.tag = "view_$i"

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
                        "guessTheLetterGuideline$whichLineForX+1",
                        "id",
                        requireActivity().packageName
                    )
                    topToBottom = resources.getIdentifier(
                        "guessTheLetterHorizontalGuideline$whichLineForY",
                        "id",
                        requireActivity().packageName

                    )
                    bottomToTop = resources.getIdentifier(
                        "guessTheLetterHorizontalGuideline$whichLineForY+1",
                        "id",
                        requireActivity().packageName
                    )

                    whichLineForX++


                }
            }

            for (i in 0..9) {
                var currentNumber = viewModel.getRandomisedNumber()
                var currentLetter = viewModel.getRandomLetterFromList()
                for (j in 0..7) {
                    if (view.tag.equals("view$currentNumber")) {
                        binding.mainBackground.findViewWithTag<TextView>("View$j").text =
                            currentLetter.toString()
                        binding.mainBackground.findViewWithTag<TextView>("View$j")
                            .setOnClickListener(View.OnClickListener {
                            })
                    } else {
                        binding.mainBackground.findViewWithTag<TextView>("View$j").text =
                            viewModel.getRandomLetterFromList().toString()
                    }
                }
                drawRound(0)
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_guessTheLetterFragment_to_victoryMenuFragment)

            }
        }
    }

    private fun drawRound(roundNumber: Int) {
        if (roundNumber == 9) {
            return
        }
        var currentNumber = viewModel.getRandomisedNumber()
        var currentLetter = viewModel.getRandomLetterFromList()
        view?.let {
            for (j in 0..7) {
                if (it.tag.equals("view$currentNumber")) {
                    binding.mainBackground.findViewWithTag<TextView>("View$j").text =
                        currentLetter.toString()
                    binding.mainBackground.findViewWithTag<TextView>("View$j")
                        .setOnClickListener(View.OnClickListener {
                            drawRound(roundNumber + 1)
                        })
                } else {
                    binding.mainBackground.findViewWithTag<TextView>("View$j").text =
                        viewModel.getRandomLetterFromList().toString()
                }

            }
        }
    }
}