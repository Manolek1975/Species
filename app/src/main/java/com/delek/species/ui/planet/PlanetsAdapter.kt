package com.delek.species.ui.planet

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.model.Planet

class PlanetsAdapter(private var planetList: List<Planet>) :
    RecyclerView.Adapter<PlanetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_planet, parent, false)
        return PlanetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        holder.render(planetList[position])
    }

    override fun getItemCount(): Int = planetList.size

}