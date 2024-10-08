package com.delek.species.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.dao.PlanetBuildsDAO
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet

class PlanetBuildsAdapter(private var build: List<Build>,
                          private var planetDao: PlanetBuildsDAO,
                          private var planet: Planet,
                          private val context: Context):
    RecyclerView.Adapter<PlanetBuildsAdapter.BuildViewHolder>() {

    class BuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buildLevel: TextView = itemView.findViewById(R.id.buildLevel)
        val buildItem: TextView = itemView.findViewById(R.id.buildItem)
        val buildDays: TextView = itemView.findViewById(R.id.buildDaysLeft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.planet_build_item, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        val build = build[position]
        val buildPlanet = planetDao.getPlanetBuild(build.id, planet)
        val level = buildPlanet.level
        holder.buildLevel.text = level.toString()
        holder.buildItem.text = build.name
        holder.buildDays.text = build.cost.toString()
        val id = context.resources.getIdentifier(build.image, "drawable", context.packageName)
        holder.buildItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.buildItem.compoundDrawablePadding = 50

    }

    override fun getItemCount(): Int = build.size

}