package com.example.elementaryreading

import android.R
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
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

            binding.imageButton7.setOnClickListener {
                //play sound button
            }
            binding.imageButton8.setOnClickListener {
                requireActivity().findNavController(com.example.elementaryreading.R.id.fragmentContainerView)
                    .navigate(com.example.elementaryreading.R.id.action_guessTheLetterFragment_to_settingsFragment)
            }
            binding.imageButton9.setOnClickListener {
                parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
            }
        }
    }

    override fun onResume() {
//                val constraintSet = ConstraintSet()
//                constraintSet.clone(binding.mainBackground)
//                constraintSet.connect(
//                    R.id.imageView,
//                    ConstraintSet.RIGHT,
//                    R.id.check_answer1,
//                    ConstraintSet.RIGHT,
//                    0
//                )
//                constraintSet.connect(
//                    R.id.imageView,
//                    ConstraintSet.TOP,
//                    R.id.check_answer1,
//                    ConstraintSet.TOP,
//                    0
//                )
//                constraintSet.applyTo(constraintLayout)
        super.onResume()
        val typeFace: Typeface? = ResourcesCompat.getFont(
            this.requireContext(),
            com.example.elementaryreading.R.font.leto_text_sans_defect
        )
        var whichLineForY = 0
        var whichLineForX = 0
        for (i in 0..7) {
            if (whichLineForX == 3) {
                whichLineForX = 0
            }
            if (i == 3) {
                whichLineForY = 2
            }
            val viewToAdd = TextView(context)
            viewToAdd.tag = "view$i"

            val lp = ConstraintLayout.LayoutParams(
                0,
                0
            ).apply {
                leftToRight = resources.getIdentifier(
                    "guessTheLetterGuideline$whichLineForX",
                    "id",
                    requireActivity().packageName
                )
                rightToLeft = resources.getIdentifier(
                    "guessTheLetterGuideline${whichLineForX + 1}",
                    "id",
                    requireActivity().packageName
                )
                topToBottom = resources.getIdentifier(
                    "guessTheLetterHorizontalGuideline$whichLineForY",
                    "id",
                    requireActivity().packageName
                )
                bottomToTop = resources.getIdentifier(
                    "guessTheLetterHorizontalGuideline${whichLineForY + 1}",
                    "id",
                    requireActivity().packageName
                )
            }
            viewToAdd.layoutParams = lp
            viewToAdd.typeface = typeFace
            viewToAdd.textSize = 64f
            binding.mainBackground.addView(viewToAdd)
            whichLineForX++
        }
        drawRound(0)
    }

    private fun drawRound(roundNumber: Int) {
        if (roundNumber == 9) {
            requireActivity().findNavController(com.example.elementaryreading.R.id.fragmentContainerView)
                .navigate(com.example.elementaryreading.R.id.action_guessTheLetterFragment_to_victoryMenuFragment)
        }
        val currentNumber = viewModel.getRandomisedNumber()
        val currentLetter = viewModel.getRandomLetterFromList()
        view?.let {
            for (j in 0..7) {
               if(it.tag == null){

               }else {
                   if (it.tag.equals("view$currentNumber")) {
                       binding.mainBackground.findViewWithTag<TextView>("view$j").text =
                           currentLetter.toString()
                       binding.mainBackground.findViewWithTag<TextView>("view$j")
                           .setOnClickListener {
                               drawRound(roundNumber + 1)
                           }
                   } else {
                       binding.mainBackground.findViewWithTag<TextView>("view$j").text =
                           viewModel.getRandomLetterFromList().toString()
                   }
               }
            }
        }
    }
}