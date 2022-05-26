package com.example.elementaryreading


import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.elementaryreading.databinding.FragmentRouletteBinding

class RouletteFragment : Fragment() {
    private lateinit var binding: FragmentRouletteBinding
    private val viewModel: RouletteViewModel by viewModels()
    private var counter = 0
    private var gCounter = 0
    private var randomizedGamesList = mutableListOf<String>()
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
        if (savedInstanceState == null) {
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.initSharedPref(requireContext())
        viewModel.recoverLetterList()
        val lettersToShowOnRoulette = mutableListOf<String>()
        binding.lettersRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.gamesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        val disabler: RecyclerView.OnItemTouchListener = RecyclerViewDisabler()
        binding.lettersRecyclerView.addOnItemTouchListener(disabler)
        binding.gamesRecyclerView.addOnItemTouchListener(disabler)
        randomizedGamesList = viewModel.randomizeGame(HelperObject.gamesList)
        if (HelperObject.currentLetterList.size < 2) {

            viewModel.generateLettersToShowOnRouletteFIRST(lettersToShowOnRoulette)
            for (i in 1..5) {
                viewModel.addRandomLetterToCurrentList()
                lettersToShowOnRoulette[5 * i] =
                    HelperObject.currentLetterList[HelperObject.currentLetterList.size - 1]
            }
            binding.lettersRecyclerView.adapter =
                RouletteLetterRecyclerViewAdapter(lettersToShowOnRoulette)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.lettersRecyclerView.smoothScrollToPosition(5)
                viewModel.playCurrentLetter(HelperObject.absoluteLetterList.indexOf(HelperObject.currentLetterList[0]))
            }, 3000)
            Handler(Looper.getMainLooper()).postDelayed({
                binding.gamesRecyclerView.adapter =
                    RouletteGameRecyclerViewAdapter(randomizedGamesList)
                binding.gamesRecyclerView.smoothScrollToPosition(randomizedGamesList.size-1)
            }, 1000)
        } else {
            viewModel.addRandomLetterToCurrentList()
            viewModel.generateLettersToShowOnRoulette(lettersToShowOnRoulette)
            lettersToShowOnRoulette.add(HelperObject.currentLetterList[HelperObject.currentLetterList.size - 1])
            binding.gamesRecyclerView.adapter =
                RouletteGameRecyclerViewAdapter(randomizedGamesList)
            binding.gamesRecyclerView.smoothScrollToPosition(0)
            binding.lettersRecyclerView.adapter =
                RouletteLetterRecyclerViewAdapter(lettersToShowOnRoulette)
            binding.lettersRecyclerView.smoothScrollToPosition(0)
            binding.lettersRecyclerView.smoothScrollToPosition(5)
            Handler(Looper.getMainLooper()).postDelayed({
                viewModel.playCurrentLetter(HelperObject.absoluteLetterList.indexOf(HelperObject.currentLetterList[HelperObject.currentLetterList.size - 1]))
            }, 3000)
            binding.gamesRecyclerView.smoothScrollToPosition(randomizedGamesList.size-2)
        }
        if(HelperObject.currentLetterList.size<6) {
            binding.lettersRecyclerView.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    counter++
                    when (counter) {
                        3 -> {
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewModel.endCoroutine()
                                binding.lettersRecyclerView.smoothScrollToPosition(10)
                                viewModel.playCurrentLetter(
                                    HelperObject.absoluteLetterList.indexOf(
                                        HelperObject.currentLetterList[1]
                                    )
                                )
                            }, 3000)
                        }
                        8 -> {
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewModel.endCoroutine()
                                binding.lettersRecyclerView.smoothScrollToPosition(15)
                                viewModel.playCurrentLetter(
                                    HelperObject.absoluteLetterList.indexOf(
                                        HelperObject.currentLetterList[2]
                                    )
                                )
                            }, 3000)
                        }
                        13 -> {
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewModel.endCoroutine()
                                binding.lettersRecyclerView.smoothScrollToPosition(20)
                                viewModel.playCurrentLetter(
                                    HelperObject.absoluteLetterList.indexOf(
                                        HelperObject.currentLetterList[3]
                                    )
                                )
                            }, 5000)
                        }
                        18 -> {
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewModel.endCoroutine()
                                binding.lettersRecyclerView.smoothScrollToPosition(25)
                                viewModel.playCurrentLetter(
                                    HelperObject.absoluteLetterList.indexOf(
                                        HelperObject.currentLetterList[4]
                                    )
                                )
                            }, 7000)

                        }
                    }
                    Log.d("test", "scrolledLETTER")


                }
            })


            binding.gamesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    gCounter++
                    if (gCounter == 1) {
                        if (randomizedGamesList[randomizedGamesList.size-1] == "guess_the_letter") {
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewModel.endCoroutine()
                                viewModel.saveLetterList()
                                requireActivity().findNavController(R.id.fragmentContainerView)
                                    .navigate(R.id.action_rouletteFragment_to_guessTheLetterFragment)
                            }, 15000)
                        } else {
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewModel.endCoroutine()
                                viewModel.saveLetterList()
                                requireActivity().findNavController(R.id.fragmentContainerView)
                                    .navigate(R.id.action_rouletteFragment_to_findTheLetterFragment)
                            }, 15000)
                        }
                    }
                }
            })
        }
        else{
            binding.gamesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    gCounter++
                    if (gCounter == 1) {
                        if (randomizedGamesList[randomizedGamesList.size-1] == "guess_the_letter") {
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewModel.endCoroutine()
                                viewModel.saveLetterList()
                                requireActivity().findNavController(R.id.fragmentContainerView)
                                    .navigate(R.id.action_rouletteFragment_to_guessTheLetterFragment)
                            }, 5000)
                        } else {
                            Handler(Looper.getMainLooper()).postDelayed({
                                viewModel.endCoroutine()
                                viewModel.saveLetterList()
                                requireActivity().findNavController(R.id.fragmentContainerView)
                                    .navigate(R.id.action_rouletteFragment_to_findTheLetterFragment)
                            }, 5000)
                        }
                    }
                }
            })






        }
    }
}


