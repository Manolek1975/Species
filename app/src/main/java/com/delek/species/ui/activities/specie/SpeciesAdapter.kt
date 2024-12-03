package com.delek.species.ui.activities.specie

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.database.model.Specie


class SpeciesAdapter(private var specieList: List<Specie>) :
    RecyclerView.Adapter<SpecieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_specie, parent, false)
        return SpecieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
        holder.render(specieList[position])
    }

    override fun getItemCount(): Int = specieList.size


}