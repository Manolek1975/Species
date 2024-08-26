package com.delek.species.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.game.Dialog
import com.delek.species.R
import com.delek.species.database.dataclass.Ship


class ShipsAdapter(private var ship: List<Ship>,
                   private val context: Context):
    RecyclerView.Adapter<ShipsAdapter.ShipViewHolder>() {

    class ShipViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val shipItem: TextView = itemView.findViewById(R.id.shipItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShipViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ship_item, parent, false)
        return ShipViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShipViewHolder, position: Int) {
        val dialog = Dialog(context)
        val ship = ship[position]
        holder.shipItem.text = ship.name
        val id = context.resources.getIdentifier(ship.image, "drawable", context.packageName)
        holder.shipItem.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, id)
        holder.shipItem.compoundDrawablePadding = 20
        holder.shipItem.setOnClickListener{
            //dialog.showSpecie(ship)
        }
    }

    override fun getItemCount(): Int = ship.size



}