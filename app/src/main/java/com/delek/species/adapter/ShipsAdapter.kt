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
import com.delek.species.dao.PlanetDAO
import com.delek.species.dao.ProdDAO
import com.delek.species.database.model.Ship
import com.delek.species.model.Game
import com.google.android.material.navigation.NavigationView


class ShipsAdapter(private var ship: List<Ship>,
                   private val context: Context):
    RecyclerView.Adapter<ShipsAdapter.ShipViewHolder>() {

    class ShipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shipItem: TextView = itemView.findViewById(R.id.shipItem)
        val nameItem: TextView = itemView.findViewById(R.id.nameItem)
        val routeItem: TextView = itemView.findViewById(R.id.routeItem)
        val daysLeftItem: TextView = itemView.findViewById(R.id.daysLeftItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ship, parent, false)
        return ShipViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShipViewHolder, position: Int) {
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)!!
        val ship = ship[position]
        val planetOrbits = PlanetDAO(context).getPlanetName(ship.orbit)
        val planetName = PlanetDAO(context).getPlanetName(ship.route)
        val daysLeft = ProdDAO(context).getDaysLeft(ship.id)
        val id = Game.getResId(ship.image, R.drawable::class.java)
        holder.shipItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0,0)
        holder.shipItem.compoundDrawablePadding = 20
        holder.nameItem.text = ship.name
        if(ship.route > 0) {
            holder.routeItem.text = context.getString(R.string.en_ruta, planetName)
            holder.daysLeftItem.text = context.getString(R.string.faltan_dias, daysLeft.toString())
        } else {
            data.edit().putInt("planet", ship.orbit).apply()
            holder.routeItem.text = context.getString(R.string.orbitando, planetOrbits)
        }

        holder.shipItem.setOnClickListener {
            if (ship.orbit > 0) {
                val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
                val item = nv.menu.getItem(9) // To Planet
                val navController = context.findNavController(R.id.nav_host)
                NavigationUI.onNavDestinationSelected(item, navController)
            }
        }
    }

    override fun getItemCount(): Int = ship.size



}