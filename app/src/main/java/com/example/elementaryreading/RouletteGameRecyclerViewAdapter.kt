package com.example.elementaryreading

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RouletteGameRecyclerViewAdapter(private val gameTypeToShowOnRoulette: List<String>) :
    RecyclerView.Adapter<RouletteGameRecyclerViewAdapter.RouletteGameRecyclerViewHolder>() {
    class RouletteGameRecyclerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView1: ImageView = itemView.findViewById(R.id.gameTypeToShow)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RouletteGameRecyclerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recycler_view_item_game, parent, false)
        return RouletteGameRecyclerViewHolder(itemView)
    }

    override fun getItemCount(): Int = gameTypeToShowOnRoulette.size
    override fun onBindViewHolder(
        holder: RouletteGameRecyclerViewAdapter.RouletteGameRecyclerViewHolder,
        position: Int
    ) {

        holder.imageView1.setImageResource(
            holder.imageView1.context.resources.getIdentifier(
                gameTypeToShowOnRoulette[position],
                "drawable",
                holder.imageView1.context.packageName
            )
        )
    }

}