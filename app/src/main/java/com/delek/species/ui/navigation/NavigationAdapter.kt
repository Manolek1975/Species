package com.delek.species.ui.navigation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.model.Planet
import com.delek.species.database.model.Ship
import com.delek.species.core.Dialog
import com.delek.species.core.Game


class NavigationAdapter(private var planets: List<Planet>,
                        private var ship: Ship,
                        private val context: Context):
    RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder>() {

    class NavigationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val planetItem: TextView = itemView.findViewById(R.id.planetItem)
        val daysLeft: TextView = itemView.findViewById(R.id.daysLeft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_navigation, parent, false)
        return NavigationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NavigationViewHolder, position: Int) {
        val dialog = Dialog(context)
        val res = context.resources
        val planet = planets[position]
        holder.planetItem.text = planet.name
        val id = Game.getResId(planet.image, R.drawable::class.java)
        holder.planetItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.planetItem.compoundDrawablePadding = 50
        val dias = distances(planet)
        holder.daysLeft.text = res.getString(R.string.total_dias, dias)
        holder.planetItem.setOnClickListener{
            dialog.insertProdShip(ship, planet, dias)
        }
    }

    private fun distances(planet: Planet): Int {
        val pos: Int
        if (ship.orbit > planet.id){
            pos = ship.orbit - planet.id
        } else {
            pos = planet.id - ship.orbit
        }
        return pos * 100
    }

    override fun getItemCount(): Int = planets.size

}