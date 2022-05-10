package com.example.elementaryreading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.elementaryreading.databinding.FragmentRouletteBinding

class RouletteFragment : Fragment() {
    private lateinit var binding: FragmentRouletteBinding
    private val viewModel: RouletteViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return if (savedInstanceState == null) {
            binding = FragmentRouletteBinding.inflate(layoutInflater)

            binding.root
        } else {
            view
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val lettersToShowOnRoulette = mutableListOf<String>()

        if (savedInstanceState == null) {

            val disabler : RecyclerView.OnItemTouchListener = RecyclerViewDisabler()
            binding.lettersRecyclerView.addOnItemTouchListener(disabler)
            binding.gamesRecyclerView.addOnItemTouchListener(disabler)
            viewModel.randomizeGame(HelperObject.gamesList)

            if(viewModel.firstTime){
                for (i in 0..3)
                    viewModel.addRandomLetterToCurrentList()
                viewModel.generateLettersToShowOnRoulette(lettersToShowOnRoulette)
                lettersToShowOnRoulette.add(HelperObject.currentLetterList[HelperObject.currentLetterList.size])
                binding.lettersRecyclerView.adapter =
                    RouletteLetterRecyclerViewAdapter(lettersToShowOnRoulette)
                binding.lettersRecyclerView.smoothScrollToPosition(5)
            }
            viewModel.addRandomLetterToCurrentList()
            viewModel.generateLettersToShowOnRoulette(lettersToShowOnRoulette)
            lettersToShowOnRoulette.add(HelperObject.currentLetterList[HelperObject.currentLetterList.size])
            binding.lettersRecyclerView.adapter =
                RouletteLetterRecyclerViewAdapter(lettersToShowOnRoulette)
            binding.lettersRecyclerView.smoothScrollToPosition(5)

            binding.gamesRecyclerView.adapter = RouletteGameRecyclerViewAdapter(HelperObject.gamesList)
            binding.gamesRecyclerView.smoothScrollToPosition(5)
           if(HelperObject.gamesList[5] == "tetris"||HelperObject.gamesList[5] == "three_in_line" ) {
               requireActivity().findNavController(R.id.fragmentContainerView)
                 .navigate(R.id.action_rouletteFragment_to_verticalGamesFragment)
           }
            if(HelperObject.gamesList[5] =="find_the_letter"){
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_settingsFragment_to_findTheLetterFragment)

            }
            if(HelperObject.gamesList[5] == "guess_the_letter"){
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_rouletteFragment_to_guessTheLetterFragment)
                }
            if(HelperObject.gamesList[5] == "slice_the_letter"){
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_rouletteFragment_to_sliceTheLetterFragment)

            }

        }
    }
}
