package com.delek.species.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.core.Dialog
import com.delek.species.R
import com.delek.species.database.dao.PlanetBuildsDAO
import com.delek.species.database.model.Build
import com.delek.species.database.model.Planet
import com.delek.species.core.Game

class BuildsAdapter(private var builds: List<Build>,
                    private var planet: Planet,
                    private val context: Context):
    RecyclerView.Adapter<BuildsAdapter.BuildViewHolder>() {

    class BuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buildItem: TextView = itemView.findViewById(R.id.buildItem)
        val buildDays: TextView = itemView.findViewById(R.id.buildDaysLeft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_build, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {

        val dialog = Dialog(context)
        val build = builds[position]

        val days = build.cost / planet.production
        holder.buildItem.text = build.name
        holder.buildDays.text = days.toString()
        val id = Game.getResId(build.image, R.drawable::class.java)
        holder.buildItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.buildItem.compoundDrawablePadding = 50

        val planetBuild = PlanetBuildsDAO(context).checkIfShipyard(planet.id, build.id)
        holder.buildItem.setOnClickListener{
            if (planetBuild && build.id == 30) {
                dialog.showAlert("Ya tienes un astillero en tu planeta")
            } else {
                dialog.showBuild(build, planet)
            }
        }
    }

    override fun getItemCount(): Int = builds.size

}