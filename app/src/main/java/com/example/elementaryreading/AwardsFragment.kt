package com.example.elementaryreading

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
import com.example.elementaryreading.databinding.FragmentAwardsBinding


class AwardsFragment : Fragment() {
    private lateinit var binding: FragmentAwardsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentAwardsBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val typeFace: Typeface? = ResourcesCompat.getFont(
            this.requireContext(),
            R.font.leto_text_sans_defect
        )
        view.setOnClickListener {
            requireActivity().onBackPressed()
        }
        if (savedInstanceState == null) {
            for (i in 0..32) {

                val viewToAdd = TextView(context)
                viewToAdd.tag = "letter$i"
                viewToAdd.id = View.generateViewId();
                val lp = ConstraintLayout.LayoutParams(
                    0,
                    0
                )
                viewToAdd.gravity = Gravity.CENTER
                viewToAdd.layoutParams = lp
                viewToAdd.typeface = typeFace
                viewToAdd.textSize = 32f
                binding.awardsBackground.addView(viewToAdd)
                val set = ConstraintSet()
                set.clone(binding.awardsBackground)
                set.connect(
                    viewToAdd.id,
                    ConstraintSet.LEFT,
                    resources.getIdentifier(
                        "whiteCircle$i",
                        "id",
                        requireActivity().packageName
                    ), ConstraintSet.LEFT
                )
                set.connect(
                    viewToAdd.id, ConstraintSet.RIGHT,
                    resources.getIdentifier(
                        "whiteCircle$i",
                        "id", requireActivity().packageName
                    ), ConstraintSet.RIGHT
                )
                set.connect(
                    viewToAdd.id,
                    ConstraintSet.TOP,
                    resources.getIdentifier(
                        "whiteCircle$i",
                        "id",
                        requireActivity().packageName
                    ), ConstraintSet.TOP
                )
                set.connect(
                    viewToAdd.id, ConstraintSet.BOTTOM,
                    resources.getIdentifier(
                        "whiteCircle$i",
                        "id",
                        requireActivity().packageName
                    ), ConstraintSet.BOTTOM
                )
                set.applyTo(binding.awardsBackground)
            }
            for (j in 0 until HelperObject.currentLetterList.size) {

                val it = binding.awardsBackground.findViewWithTag<TextView>(
                    "letter${
                        HelperObject.absoluteLetterList.indexOf(HelperObject.currentLetterList[j])
                    }"
                )
                it.text = HelperObject.currentLetterList[j]
            }


        }
    }
}