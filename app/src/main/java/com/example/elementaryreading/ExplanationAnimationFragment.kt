package com.example.elementaryreading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.example.elementaryreading.databinding.ExplanationAnimationBinding
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers


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


//        if (savedInstanceState == null) {
//            viewModel.playExplanation()
//            if (HelperObject.firstTime) {
//                HelperObject.firstTime = false
//                requireActivity().findNavController(R.id.fragmentContainerView)
//                    .navigate(R.id.action_explanationAnimation_to_rouletteFragment)
//            } else {
//                requireActivity().onBackPressed()
//            }
//        }
    }

}




