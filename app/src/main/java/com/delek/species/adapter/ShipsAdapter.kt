package com.delek.species.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.game.Dialog
import com.delek.species.R
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dataclass.Ship


class ShipsAdapter(private var ship: List<Ship>,
                   private val context: Context):
    RecyclerView.Adapter<ShipsAdapter.ShipViewHolder>() {

    class ShipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shipItem: TextView = itemView.findViewById(R.id.shipItem)
        val routeItem: TextView = itemView.findViewById(R.id.routeItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ship_item, parent, false)
        return ShipViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShipViewHolder, position: Int) {
        val dialog = Dialog(context)
        val ship = ship[position]
        val planetName = PlanetDAO(context).getPlanetName(ship.route)
        holder.shipItem.text = ship.name
        holder.routeItem.text = "En ruta al planeta $planetName"
        val id = context.resources.getIdentifier(ship.image, "drawable", context.packageName)
        holder.shipItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0,0)
        holder.shipItem.compoundDrawablePadding = 20
        holder.shipItem.setOnClickListener{
            //dialog.showSpecie(ship)
        }
    }

    override fun getItemCount(): Int = ship.size



}