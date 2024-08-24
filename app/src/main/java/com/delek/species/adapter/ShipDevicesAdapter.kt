package com.delek.species.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ShipDevicesDAO
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Device
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Ship

class ShipDevicesAdapter(private var device: List<Device>,
                         //private var shipDevicesDAO: ShipDevicesDAO,
                         //private var ship: Ship,
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
        //val shipDevice = shipDevicesDAO.getShipDevice(ship, device)
        holder.deviceItem.text = device.name
        holder.deviceType.text = device.type.toString()
        val id = context.resources.getIdentifier(device.image, "drawable", context.packageName)
        holder.deviceItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.deviceItem.compoundDrawablePadding = 50

    }

    override fun getItemCount(): Int = device.size

}