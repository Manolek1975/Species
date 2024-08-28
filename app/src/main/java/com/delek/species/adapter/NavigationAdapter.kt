package com.delek.species.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.activities.PlanetActivity
import com.delek.species.activities.ShipActivity
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Ship

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
        val res = context.resources
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val planet = planets[position]
        holder.planetItem.text = planet.name
        val id = context.resources.getIdentifier(planet.image, "drawable", context.packageName)
        holder.planetItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.planetItem.compoundDrawablePadding = 50
        val dias = distances(planet)
        holder.daysLeft.text = res.getString(R.string.dias, dias)
        holder.planetItem.setOnClickListener{
            if (dias == 0) {
                val i = Intent(context, PlanetActivity::class.java)
                context.startActivity(i)
            } else {
                val i = Intent(context, ShipActivity::class.java)
                ShipDAO(context).updateRouteShip(ship.id, planet.id, dias)
                context.startActivity(i)
            }

            data.edit().putInt("planet", planet.id).apply()
            //ShipDAO(context).updateOrbitShip(planet.id, data.getInt("specie", 0))

        }
    }

    private fun distances(planet: Planet): Int {
        if (planet.position <= ship.orbit){
            return (ship.orbit - planet.position)*400
        } else
            return (planet.position - ship.orbit)*400
    }

    override fun getItemCount(): Int = planets.size

}