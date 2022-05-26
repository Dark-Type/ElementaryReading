package com.example.elementaryreading

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveInitializer
import app.rive.runtime.kotlin.core.Fit
import com.example.elementaryreading.databinding.ExplanationAnimationBinding


class ExplanationAnimationFragment : Fragment() {
    private lateinit var binding: ExplanationAnimationBinding
    private val viewModel: ExplanationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = ExplanationAnimationBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        AppInitializer.getInstance(requireContext())
            .initializeComponent(RiveInitializer::class.java)

        if (savedInstanceState == null) {
            val animationView = binding.mainAnimation

            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.playExplanation()
            }, 1000)

            Handler(Looper.getMainLooper()).postDelayed({
                animationView.fit = Fit.FILL
                animationView.setRiveResource(
                    R.raw.animations,
                    artboardName = "first",
                    animationName = "talking",
                    autoplay = true
                )

            }, 1000)
            Handler(Looper.getMainLooper()).postDelayed({
                animationView.setRiveResource(

                    R.raw.animations,
                    artboardName = "second",
                    animationName = "GoToRoulette",
                    autoplay = true

                )
            }, 11000)
            Handler(Looper.getMainLooper()).postDelayed({
                animationView.setRiveResource(

                    R.raw.animations,
                    artboardName = "third",
                    animationName = "Animation 1",
                    autoplay = true

                )
            }, 12000)
            Handler(Looper.getMainLooper()).postDelayed({
                animationView.setRiveResource(

                    R.raw.animations,
                    artboardName = "fourth",
                    animationName = "GoToFindTheLetter",
                    autoplay = true

                )
            }, 21000)
            Handler(Looper.getMainLooper()).postDelayed({
                animationView.setRiveResource(

                    R.raw.animations,
                    artboardName = "fifth",
                    animationName = "Animation 1",
                    autoplay = true

                )
            }, 22000)
            Handler(Looper.getMainLooper()).postDelayed({
                animationView.setRiveResource(

                    R.raw.animations,
                    artboardName = "sixth",
                    animationName = "Animation 1",
                    autoplay = true

                )
            }, 41000)
            Handler(Looper.getMainLooper()).postDelayed({
                animationView.setRiveResource(

                    R.raw.animations,
                    artboardName = "seventh",
                    animationName = "Animation 1",
                    autoplay = true

                )
            }, 42000)
            Handler(Looper.getMainLooper()).postDelayed({
                animationView.setRiveResource(

                    R.raw.animations,
                    artboardName = "eight",
                    animationName = "Animation 1",
                    autoplay = true

                )
            }, 43000)

            Handler(Looper.getMainLooper()).postDelayed({
                if (HelperObject.firstTime) {
                    HelperObject.firstTime = false
                    requireActivity().findNavController(R.id.fragmentContainerView)
                        .navigate(R.id.action_explanationAnimation_to_rouletteFragment)
                } else {
                    requireActivity().onBackPressed()
                }
            }, 66000)
        }
    }

}




