package com.delek.species.ui.star

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.model.Star


class StarsAdapter(private var starList: List<Star>) :
    RecyclerView.Adapter<StarViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_star, parent, false)
        return StarViewHolder(view)
    }

    override fun onBindViewHolder(holder: StarViewHolder, position: Int) {
        holder.render(starList[position])
    }

    override fun getItemCount(): Int = starList.size

}