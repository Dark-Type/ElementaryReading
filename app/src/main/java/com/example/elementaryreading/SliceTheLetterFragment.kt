package com.example.elementaryreading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.elementaryreading.databinding.FragmentSliceTheLetterBinding

class SliceTheLetterFragment : Fragment() {
    private lateinit var binding: FragmentSliceTheLetterBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentSliceTheLetterBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null) {
binding.imageButton10.setOnClickListener(View.OnClickListener {
    requireActivity().findNavController(R.id.fragmentContainerView)
        .navigate(R.id.action_sliceTheLetterFragment_to_settingsFragment)
})


        }
    }
}