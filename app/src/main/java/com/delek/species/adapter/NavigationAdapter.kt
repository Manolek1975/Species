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
import com.delek.species.database.dao.ProdDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Ship
import com.delek.species.model.Dialog
import com.google.android.material.navigation.NavigationView

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
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val planet = planets[position]
        holder.planetItem.text = planet.name
        val id = context.resources.getIdentifier(planet.image, "drawable", context.packageName)
        holder.planetItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.planetItem.compoundDrawablePadding = 50
        val dias = distances(planet)
        holder.daysLeft.text = res.getString(R.string.dias, dias)
        holder.planetItem.setOnClickListener{
            data.edit().putInt("planet", planet.id).apply()
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val navController = context.findNavController(R.id.nav_host)
            var item = nv.menu.getItem(9) // To Planet
            if (dias != 0) {
                item = nv.menu.getItem(4) // To Ships
                ShipDAO(context).updateRouteShip(ship.id, planet.id)
                dialog.insertProdShip(ship, planet, dias)
            }
            NavigationUI.onNavDestinationSelected(item, navController)
        }
    }

    private fun distances(planet: Planet): Int {
        if (planet.position <= ship.orbit){
            return (ship.orbit - planet.position)*100
        } else
            return (planet.position - ship.orbit)*100
    }

    override fun getItemCount(): Int = planets.size

}