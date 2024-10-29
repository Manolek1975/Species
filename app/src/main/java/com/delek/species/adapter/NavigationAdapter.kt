package com.delek.species.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Ship
import com.delek.species.model.Dialog


class NavigationAdapter(private var planets: List<Planet>,
                        private var ship: Ship,
                        private val context: Context):
    RecyclerView.Adapter<NavigationAdapter.NavigationViewHolder>() {

    class NavigationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val planetItem: TextView = itemView.findViewById(R.id.planetItem)
        val daysLeft: TextView = itemView.findViewById(R.id.daysLeft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NavigationViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.navigation_item, parent, false)
        return NavigationViewHolder(view)
    }

    override fun onBindViewHolder(holder: NavigationViewHolder, position: Int) {
        val dialog = Dialog(context)
        val res = context.resources
        val planet = planets[position]
        holder.planetItem.text = planet.name
        val id = context.resources.getIdentifier(planet.image, "drawable", context.packageName)
        holder.planetItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.planetItem.compoundDrawablePadding = 50
        //val dias = distances(planet)
        val dias = planet.position * 100
        holder.daysLeft.text = res.getString(R.string.dias, dias)
        holder.planetItem.setOnClickListener{
            dialog.insertProdShip(ship, planet, dias)
        }
    }

/*    private fun distances(planet: Planet): Int {
        if (planet.position <= ship.orbit){
            return (ship.orbit - planet.position)*100
        } else
            return (planet.position - ship.orbit)*100
    }*/

    override fun getItemCount(): Int = planets.size

}