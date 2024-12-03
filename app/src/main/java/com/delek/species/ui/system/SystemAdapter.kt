package com.delek.species.ui.system

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.model.Planet

class SystemAdapter(private var planetList: List<Planet>) :
    RecyclerView.Adapter<SystemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SystemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_planet, parent, false)
        return SystemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SystemViewHolder, position: Int) {
        holder.render(planetList[position])
    }

    override fun getItemCount(): Int = planetList.size

}