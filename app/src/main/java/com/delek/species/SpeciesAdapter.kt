package com.delek.species

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.database.Specie


class SpeciesAdapter(private var species: List<Specie>):
    RecyclerView.Adapter<SpeciesAdapter.SpecieViewHolder>() {

    class SpecieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val specieName: TextView = itemView.findViewById(R.id.specieName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.specie_item, parent, false)
        return SpecieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
        val specie = species[position]
        holder.specieName.text = specie.name
        val context: Context = holder.specieName.context
        val id = context.resources.getIdentifier(specie.image, "drawable", context.packageName)
        holder.specieName.setCompoundDrawablesWithIntrinsicBounds(id,0,0,0)
        holder.specieName.setCompoundDrawablePadding(50)

    }

    override fun getItemCount(): Int = species.size

    fun refreshData(newSpecies: List<Specie>){
        species = newSpecies
        notifyDataSetChanged()
    }

}