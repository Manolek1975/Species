package com.delek.species.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.game.Dialog
import com.delek.species.R
import com.delek.species.activities.NavigationActivity
import com.delek.species.database.dataclass.Device
import com.delek.species.database.dataclass.Planet

class ShipDevicesAdapter(private var device: List<Device>,
                         private var planet: Planet,
                         private var shipId: Int,
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
            checkDevice(device, planet)
            if (device.type == 1){
                val i = Intent(context, NavigationActivity::class.java)
                i.putExtra("shipId", shipId)
                context.startActivity(i)
            }
        }

    }

    private fun checkDevice(device: Device, planet: Planet){
        when (device.type to planet.explore) {
            0 to 0 -> Dialog(context).notExplored()
            0 to 1 -> Dialog(context).createColony(planet, shipId)
            0 to 2 -> Dialog(context).alreadyColony()
            3 to 0 -> Dialog(context).explorePlanet(planet)
            3 to 1 -> Dialog(context).alreadyExplored()
            3 to 2 -> Dialog(context).alreadyExplored()
        }
    }

    override fun getItemCount(): Int = device.size

}