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
        val data = context.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        val planet = planets[position]
        holder.planetItem.text = planet.name
        val id = context.resources.getIdentifier(planet.image, "drawable", context.packageName)
        holder.planetItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.planetItem.compoundDrawablePadding = 50
        val dias = ship.days + 400
        holder.daysLeft.text = res.getString(R.string.dias, dias)
        holder.planetItem.setOnClickListener{
            val intent = Intent(context, PlanetActivity::class.java)
            data.edit().putInt("planet", planet.id).apply()
            ShipDAO(context).updateOrbitShip(planet.id, data.getInt("specie", 0))
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = planets.size

}