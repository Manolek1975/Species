package com.delek.species.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.activities.SidebarActivity
import com.delek.species.dao.PlanetDAO
import com.delek.species.dao.ShipDAO
import com.delek.species.dao.SpecieDAO
import com.delek.species.database.dataclass.Planet
import com.delek.species.model.Game
import com.google.android.material.navigation.NavigationView

class PlanetsAdapter(private var planets: List<Planet>,
                     private val context: Context):
    RecyclerView.Adapter<PlanetsAdapter.PlanetViewHolder>() {

    class PlanetViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val planetItem: TextView = itemView.findViewById(R.id.planetItem)
        val planetType: TextView = itemView.findViewById(R.id.planetType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanetViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.planet_item, parent, false)
        return PlanetViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanetViewHolder, position: Int) {
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val ship = data.getInt("ship", 0)
        val specie = SpecieDAO(context).getSpecieById(specieId)

        val planet = planets[position]
        val type = PlanetDAO(context).getType(planet.type)
        if (planet.owner == specieId){
            holder.planetItem.setTextColor(Color.parseColor(specie.color))
            holder.planetType.setTextColor(Color.parseColor(specie.color))
        }
        holder.planetItem.text = planet.name
        holder.planetType.text = type.name
        val id = Game.getResId(planet.image, R.drawable::class.java)
        holder.planetItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.planetItem.compoundDrawablePadding = 50

        holder.planetItem.setOnClickListener{
            if (ship==0)
                ShipDAO(context).updateOrbitShip(planet.id, specieId)
            data.edit().putInt("planet", planet.id).apply()
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(9) //To Planet
            val navController = context.findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }
    }

    override fun getItemCount(): Int = planets.size

}