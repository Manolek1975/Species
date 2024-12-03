package com.delek.species.ui.build

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.model.Build
import com.delek.species.database.model.Planet

class BuildsAdapter(private var buildLists: List<Build>, private var planet: Planet) :
    RecyclerView.Adapter<BuildViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_build, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        holder.render(buildLists[position], planet)
    }

    override fun getItemCount(): Int = buildLists.size

}