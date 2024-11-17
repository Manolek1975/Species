package com.delek.species.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.dataclass.Device
import com.delek.species.model.Game


class ShipDevicesAdapter(private var device: List<Device>):
    RecyclerView.Adapter<ShipDevicesAdapter.BuildViewHolder>() {

    class BuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceItem: TextView = itemView.findViewById(R.id.deviceItem)
        val rootView: View = itemView.rootView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ship_device_item, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        val device = device[position]
        holder.deviceItem.text = device.name
        val id = Game.getResId(device.image, R.drawable::class.java)
        holder.deviceItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.deviceItem.compoundDrawablePadding = 50

        holder.rootView.setOnClickListener {
            onItemClickListener?.let{
                it(device)
            }
        }
    }

    override fun getItemCount(): Int = device.size

    private var onItemClickListener:((Device)->Unit)? = null
    fun setOnItemClickListener(listener: (Device)->Unit) {
        onItemClickListener = listener
    }
}


/*    private fun checkDevice(device: Device, planet: Planet){
        when (device.type to planet.explore) {
            0 to 0 -> Dialog(context).notExplored()
            0 to 1 -> Dialog(context).createColony(planet, shipId)
            0 to 2 -> Dialog(context).alreadyColony()
            3 to 0 -> Dialog(context).explorePlanet(planet)
            3 to 1 -> Dialog(context).alreadyExplored()
            3 to 2 -> Dialog(context).alreadyExplored()
        }
    }*/




