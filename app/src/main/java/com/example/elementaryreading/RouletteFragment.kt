package com.example.elementaryreading

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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

            binding.lettersRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            binding.gamesRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

            val disabler: RecyclerView.OnItemTouchListener = RecyclerViewDisabler()
            binding.lettersRecyclerView.addOnItemTouchListener(disabler)
            binding.gamesRecyclerView.addOnItemTouchListener(disabler)
            viewModel.randomizeGame(HelperObject.gamesList)

            if (viewModel.firstTime) {
                for (i in 0..3)
                    viewModel.addRandomLetterToCurrentList()
                viewModel.generateLettersToShowOnRoulette(lettersToShowOnRoulette)
                lettersToShowOnRoulette.add(HelperObject.currentLetterList[HelperObject.currentLetterList.size - 1])
                binding.lettersRecyclerView.adapter =
                    RouletteLetterRecyclerViewAdapter(lettersToShowOnRoulette)
                binding.lettersRecyclerView.smoothScrollToPosition(5)
            }
            viewModel.addRandomLetterToCurrentList()
            viewModel.generateLettersToShowOnRoulette(lettersToShowOnRoulette)
            lettersToShowOnRoulette.add(HelperObject.currentLetterList[HelperObject.currentLetterList.size - 1])
            binding.lettersRecyclerView.adapter =
                RouletteLetterRecyclerViewAdapter(lettersToShowOnRoulette)
            binding.lettersRecyclerView.smoothScrollToPosition(5)

            binding.gamesRecyclerView.adapter =
                RouletteGameRecyclerViewAdapter(HelperObject.gamesList)
            binding.gamesRecyclerView.smoothScrollToPosition(5)
            
            binding.lettersRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    Log.d("test", "scrolled")

                }

            })
            binding.gamesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)

                    Log.d("test", "scrolled")

                }

            })
//            if (HelperObject.gamesList[5] == "tetris" || HelperObject.gamesList[5] == "three_in_line") {
//                requireActivity().findNavController(R.id.fragmentContainerView)
//                  .navigate(R.id.action_rouletteFragment_to_verticalGamesFragment)
//            }
//            if (HelperObject.gamesList[5] == "find_the_letter") {
//                requireActivity().findNavController(R.id.fragmentContainerView)
//                    .navigate(R.id.action_settingsFragment_to_findTheLetterFragment)
//
//            }
//            if (HelperObject.gamesList[5] == "guess_the_letter") {
//                requireActivity().findNavController(R.id.fragmentContainerView)
//                    .navigate(R.id.action_rouletteFragment_to_guessTheLetterFragment)
//            }
//            if (HelperObject.gamesList[5] == "slice_the_letter") {
//                requireActivity().findNavController(R.id.fragmentContainerView)
//                    .navigate(R.id.action_rouletteFragment_to_sliceTheLetterFragment)
//
//            }

        }
    }
}
