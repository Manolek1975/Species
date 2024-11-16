package com.delek.species.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.dataclass.Build
import com.delek.species.model.Game

class PlanetOrbitalAdapter(private var build: List<Build>):
    RecyclerView.Adapter<PlanetOrbitalAdapter.BuildViewHolder>() {

    class BuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orbitalItem: TextView = itemView.findViewById(R.id.orbitalItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.orbital_item, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        val build = build[position]
        val id = Game.getResId(build.image, R.drawable::class.java)
        holder.orbitalItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.orbitalItem.compoundDrawablePadding = 50
    }

    override fun getItemCount(): Int = build.size

}