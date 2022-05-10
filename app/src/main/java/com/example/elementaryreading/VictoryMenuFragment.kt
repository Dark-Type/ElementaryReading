package com.example.elementaryreading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.elementaryreading.databinding.FragmentVictoryMenuBinding

class VictoryMenuFragment : Fragment() {
    private lateinit var binding: FragmentVictoryMenuBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentVictoryMenuBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            binding.imageButton3.setOnClickListener(View.OnClickListener {
                requireActivity().onBackPressed()
            })
            binding.imageButton4.setOnClickListener(View.OnClickListener {
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_victoryMenuFragment_to_yesNoMenuFragment)
            })
            binding.imageButtonS.setOnClickListener(View.OnClickListener {
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_victoryMenuFragment_to_settingsFragment)
            })
        }
    }
}