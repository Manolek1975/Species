package com.delek.species.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.Dialog
import com.delek.species.R
import com.delek.species.database.dataclass.Device
import com.delek.species.database.dataclass.Planet

class ShipDevicesAdapter(private var device: List<Device>,
                         private var planet: Planet,
                         private val context: Context):
    RecyclerView.Adapter<ShipDevicesAdapter.BuildViewHolder>() {

    class BuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceItem: TextView = itemView.findViewById(R.id.deviceItem)
        val deviceType: TextView = itemView.findViewById(R.id.deviceType)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ship_device_item, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        val device = device[position]
        holder.deviceItem.text = device.name
        holder.deviceType.text = device.type.toString()
        val id = context.resources.getIdentifier(device.image, "drawable", context.packageName)
        holder.deviceItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.deviceItem.compoundDrawablePadding = 50

        holder.deviceItem.setOnClickListener{
            if (device.type == 0) Dialog(context).showColony(planet)
        }

    }

    override fun getItemCount(): Int = device.size

}