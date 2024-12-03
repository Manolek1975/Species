package com.delek.species.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.ui.activities.SidebarActivity
import com.delek.species.database.model.Build
import com.delek.species.core.Game
import com.google.android.material.navigation.NavigationView


class PlanetOrbitalAdapter(private var build: List<Build>, private val context: Context):
    RecyclerView.Adapter<PlanetOrbitalAdapter.BuildViewHolder>() {

    class BuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orbitalItem: TextView = itemView.findViewById(R.id.orbitalItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_orbital, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        val build = build[position]
        val id = Game.getResId(build.image, R.drawable::class.java)
        holder.orbitalItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.orbitalItem.compoundDrawablePadding = 50

        holder.orbitalItem.setOnClickListener {
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(12) // To Shipyard
            val navController = (context).findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }
    }

    override fun getItemCount(): Int = build.size

}