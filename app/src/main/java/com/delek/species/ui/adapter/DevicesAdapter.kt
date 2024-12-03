package com.delek.species.ui.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.dao.DeviceDAO
import com.delek.species.database.model.Device
import com.delek.species.core.Game


class DevicesAdapter(private var device: List<Device>, private val context: Context):
    RecyclerView.Adapter<DevicesAdapter.BuildViewHolder>() {

    class BuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val deviceView: ImageView = itemView.findViewById(R.id.deviceView)
        val deviceType: TextView = itemView.findViewById(R.id.deviceType)
        val deviceName: TextView = itemView.findViewById(R.id.deviceName)
        val deviceDescription: TextView = itemView.findViewById(R.id.deviceDescription)
        val deviceDays: TextView = itemView.findViewById(R.id.deviceDays)

        val rootView: View = itemView.rootView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_device, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        val device = device[position]
        val typeName = DeviceDAO(context).getTypeName(device.type)
        val id = Game.getResId(device.image, R.drawable::class.java)
        holder.deviceView.setImageResource(id)
        holder.deviceType.text = typeName
        holder.deviceType.paintFlags = Paint.UNDERLINE_TEXT_FLAG
        holder.deviceName.text = device.name
        holder.deviceDescription.text = device.desc
        holder.deviceDays.text = device.cost.toString()

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




