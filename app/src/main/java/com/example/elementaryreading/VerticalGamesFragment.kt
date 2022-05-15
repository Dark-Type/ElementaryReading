package com.example.elementaryreading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.elementaryreading.databinding.FragmentVerticalGamesBinding

class VerticalGamesFragment : Fragment() {
    private lateinit var binding: FragmentVerticalGamesBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentVerticalGamesBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
            binding.imageButton11.setOnClickListener {
                parentFragmentManager.beginTransaction().detach(this).attach(this).commit()
            }
            binding.imageButton7.setOnClickListener {
//pause
            }
            binding.imageButton9.setOnClickListener {
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_verticalGamesFragment_to_settingsFragment)
            }
        }
    }
}