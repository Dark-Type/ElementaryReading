package com.example.elementaryreading.fragments.findTheLetterFragment


import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.elementaryreading.helperObject.HelperObject
import com.example.elementaryreading.R
import com.example.elementaryreading.databinding.FragmentFindTheLetterBinding


class FindTheLetterFragment : Fragment() {
    private var backgroundWidth = 0
    private var backgroundHeight = 0
    private var imageWidth = 0
    private var roundNumber = 0
    private var currentLetterIndex = 0
    private lateinit var textView: TextView

    private var imageHeight = 0
    private var isFirstRoundDisplayed = false
    private fun measureThisImageView(layout: ImageView) {
        val vto: ViewTreeObserver = layout.viewTreeObserver
        vto.addOnGlobalLayoutListener {
            imageWidth = layout.measuredWidth
            imageHeight = layout.measuredHeight
        }
    }

    private fun measureThisLayout(layout: ConstraintLayout) {
        val vto: ViewTreeObserver = layout.viewTreeObserver
        vto.addOnGlobalLayoutListener {
            backgroundWidth = layout.measuredWidth
            backgroundHeight = layout.measuredHeight
        }
    }

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
            textView = TextView(requireContext())

            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.speechRecognizer.getViewState().observe(viewLifecycleOwner) { viewState ->
                    viewModel.render(viewState)
                }


                fun eventRender(uiOutput: FindTheLetterViewModel.Events?) {
                    if (uiOutput == null) return

                    if (uiOutput.endOfSpeech) {
                        binding.micro.visibility = View.INVISIBLE
                    }

                    if (uiOutput.gameEnd) {
                        roundNumber++
                        (textView.parent as? ViewGroup)?.removeView(textView)
                        drawRound()
                    }

                }
                viewModel.getEventLiveData().observe(viewLifecycleOwner) { eventLiveData ->
                    eventRender(eventLiveData)
                }
            }, 1000)


            measureThisLayout(binding.background)
            measureThisImageView(binding.findTheLetterBackground)
            if ((1 until 100).random() % 2 == 0) {
                binding.findTheLetterBackground.setImageResource(R.drawable.find_the_letter_background_2)
            } else {
                binding.findTheLetterBackground.setImageResource(R.drawable.find_the_letter_background_1)
            }
            binding.imageButton7.setOnClickListener {
                viewModel.playCurrentLetter(currentLetterIndex)
            }
            binding.imageButton8.setOnClickListener {
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_findTheLetterFragment_to_settingsFragment)
                isFirstRoundDisplayed = false
            }
            binding.imageButton9.setOnClickListener {
                parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        measureThisLayout(binding.background)
        measureThisImageView(binding.findTheLetterBackground)
        isFirstRoundDisplayed = false
        viewModel.stopListeningFLF()
        Handler(Looper.getMainLooper()).postDelayed({
            drawRound()
        }, 1000)
    }

    private fun drawRound() {
        if (roundNumber == 9) {
            requireActivity().findNavController(R.id.fragmentContainerView)
                .navigate(R.id.action_findTheLetterFragment_to_victoryMenuFragment)
        }

        val lp = RelativeLayout.LayoutParams(300, 300)

        lp.leftMargin = (0..imageWidth).random()
        lp.topMargin = ((backgroundHeight - imageHeight) /
                2..((backgroundHeight - imageHeight) / 2 + (imageHeight / 1.3)).toInt()).random()
        val typeFace: Typeface? =
            ResourcesCompat.getFont(this.requireContext(), R.font.leto_text_sans_defect)

        with(textView) {
            textSize = 64f
            width = 200
            height = 200
            typeface = typeFace
            layoutParams = lp
            text = HelperObject.getRandomLetter()
            setTextColor(Color.parseColor("#1D1686"))
        }

        binding.lettersBackground.addView(textView)
        currentLetterIndex = HelperObject.absoluteLetterList.indexOf(textView.text)
        viewModel.changeCurrentLetter(textView.text.toString())
        binding.whiteBackground7.setOnClickListener {

            if (viewModel.isListeningFLF()) {
                viewModel.stopListeningFLF()
            } else {
                viewModel.startListeningFLF()
                binding.micro.visibility = View.VISIBLE
            }
            if (viewModel.checkTheLetterFLF()) {

                roundNumber++
                drawRound()

            }
        }
    }


}

