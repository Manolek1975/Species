package com.delek.species.ui.surface

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.dao.PlanetBuildsDAO
import com.delek.species.database.model.Build
import com.delek.species.database.model.Planet
import com.delek.species.core.Game

class SurfaceBuildAdapter(private var build: List<Build>,
                          private var planetDao: PlanetBuildsDAO,
                          private var planet: Planet,
                          private val context: Context):
    RecyclerView.Adapter<SurfaceBuildAdapter.BuildViewHolder>() {

    class BuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buildLevel: TextView = itemView.findViewById(R.id.buildLevel)
        val buildItem: TextView = itemView.findViewById(R.id.buildItem)
        val buildDays: TextView = itemView.findViewById(R.id.buildDaysLeft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_planet_build, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        val build = build[position]
        val buildPlanet = planetDao.getPlanetBuildById(build.id, planet)
        val level = buildPlanet.level
        holder.buildLevel.text = level.toString()
        holder.buildItem.text = build.name
        holder.buildDays.text = context.getString(R.string.multiplier, level.toString())
        holder.buildDays.setCompoundDrawablesWithIntrinsicBounds(0,0, R.drawable.human,0)
        val id = Game.getResId(build.image, R.drawable::class.java)
        holder.buildItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.buildItem.compoundDrawablePadding = 50

    }

    override fun getItemCount(): Int = build.size

}