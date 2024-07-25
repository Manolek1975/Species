package com.delek.species.database

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R

class PlanetsAdapter(private var planets: List<Planet>,
                     private val context: Context):
    RecyclerView.Adapter<PlanetsAdapter.PlanetViewHolder>() {

    class PlanetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val planetItem: TextView = itemView.findViewById(R.id.planetItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.planet_item, parent, false)
        return PlanetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        val planet = planets[position]
        holder.planetItem.text = planet.name
        val id = context.resources.getIdentifier(planet.image, "drawable", context.packageName)
        holder.planetItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.planetItem.compoundDrawablePadding = 50
        holder.planetItem.setOnClickListener{
            dialog(planet)
        }
    }
    override fun getItemCount(): Int = planets.size
    private fun dialog(planet: Planet) {

    }

}