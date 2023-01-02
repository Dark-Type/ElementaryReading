package com.example.elementaryreading.fragments.rouletteFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.elementaryreading.R

class RouletteLetterRecyclerViewAdapter(private val lettersToShowOnRoulette: List<String>) :
    RecyclerView.Adapter<RouletteLetterRecyclerViewAdapter.RouletteLetterRecyclerViewHolder>() {
    class RouletteLetterRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView1: TextView = itemView.findViewById(R.id.letterTextView)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RouletteLetterRecyclerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item_letter, parent, false)
        return RouletteLetterRecyclerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RouletteLetterRecyclerViewHolder, position: Int) {
        holder.textView1.text = lettersToShowOnRoulette[position]
    }

    override fun getItemCount(): Int = lettersToShowOnRoulette.size

}