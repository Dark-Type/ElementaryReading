package com.example.elementaryreading.fragments.guessTheLetterFragment

import android.graphics.Typeface
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.elementaryreading.helperObject.HelperObject
import com.example.elementaryreading.R
import com.example.elementaryreading.databinding.FragmentGuessTheLetterBinding


class GuessTheLetterFragment : Fragment() {
    private lateinit var binding: FragmentGuessTheLetterBinding
    private val viewModel: GuessTheLetterViewModel by viewModels()
    private var indexForPlayer = 0
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
                viewModel.playCurrentLetter(indexForPlayer)
            }
            binding.imageButton8.setOnClickListener {
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_guessTheLetterFragment_to_settingsFragment)
            }
            binding.imageButton9.setOnClickListener {
                parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val typeFace: Typeface? = ResourcesCompat.getFont(
            this.requireContext(),
            R.font.leto_text_sans_defect
        )
        for (i in 0..7) {
            val viewToAdd = TextView(context)
            viewToAdd.tag = "view$i"
            viewToAdd.id = View.generateViewId();
            val lp = ConstraintLayout.LayoutParams(
                0,
                0
            )
            viewToAdd.gravity = Gravity.CENTER
            viewToAdd.layoutParams = lp
            viewToAdd.typeface = typeFace
            viewToAdd.textSize = 64f
            binding.mainBackground.addView(viewToAdd)
            val set = ConstraintSet()
            set.clone(binding.mainBackground)
            set.connect(
                viewToAdd.id,
                ConstraintSet.LEFT,
                resources.getIdentifier(
                    "whiteBackground$i",
                    "id",
                    requireActivity().packageName
                ), ConstraintSet.LEFT
            )
            set.connect(
                viewToAdd.id, ConstraintSet.RIGHT,
                resources.getIdentifier(
                    "whiteBackground$i",
                    "id", requireActivity().packageName
                ), ConstraintSet.RIGHT
            )
            set.connect(
                viewToAdd.id,
                ConstraintSet.TOP,
                resources.getIdentifier(
                    "whiteBackground$i",
                    "id",
                    requireActivity().packageName
                ), ConstraintSet.TOP
            )
            set.connect(
                viewToAdd.id, ConstraintSet.BOTTOM,
                resources.getIdentifier(
                    "whiteBackground$i",
                    "id",
                    requireActivity().packageName
                ), ConstraintSet.BOTTOM
            )
            set.applyTo(binding.mainBackground)
        }
        drawRound(0)
    }

    private fun drawRound(roundNumber: Int) {
        if (roundNumber == 9) {
            requireActivity().findNavController(R.id.fragmentContainerView)
                .navigate(R.id.action_guessTheLetterFragment_to_victoryMenuFragment)
        }
        val currentNumber = viewModel.getRandomisedNumber()
        val currentLetter = viewModel.getRandomLetterFromList()
        indexForPlayer = HelperObject.absoluteLetterList.indexOf(currentLetter)
        for (j in 0..7) {
            val it = binding.mainBackground.findViewWithTag<TextView>("view$j")
            if (it.tag == "view$currentNumber") {
                binding.mainBackground.findViewWithTag<TextView>("view$j").text =
                    currentLetter
                binding.mainBackground.findViewWithTag<TextView>("view$j")
                    .setOnClickListener {
                        binding.mainBackground.findViewWithTag<TextView>("view$j")
                            .setOnClickListener(null)
                        drawRound(roundNumber + 1)
                    }
            } else {
                binding.mainBackground.findViewWithTag<TextView>("view$j").text =
                    viewModel.getAbsoluteRandomLetter()
            }
        }
        viewModel.playCurrentLetter(indexForPlayer)
    }
}
