package com.delek.species.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.activities.SidebarActivity
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.database.dataclass.Planet
import com.google.android.material.navigation.NavigationView

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
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specie = data.getInt("specie", 0)
        val ship = data.getInt("ship", 0)
        val planet = planets[position]
        //val star = StarDAO(context).getStarById(planet.star)
        holder.planetItem.text = planet.name
        val id = context.resources.getIdentifier(planet.image, "drawable", context.packageName)
        holder.planetItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.planetItem.compoundDrawablePadding = 50

        println(planet.star)

        holder.planetItem.setOnClickListener{
            if (ship==0){
                ShipDAO(context).updateOrbitShip(planet.id, specie)
            }
            data.edit().putInt("planet", planet.id).apply()
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(9) //To Planet
            val navController = context.findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }
    }

    override fun getItemCount(): Int = planets.size

}