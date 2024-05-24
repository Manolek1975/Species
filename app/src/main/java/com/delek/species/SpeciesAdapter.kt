package com.delek.species

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.database.Specie


class SpeciesAdapter(private var species: List<Specie>, context: Context):
    RecyclerView.Adapter<SpeciesAdapter.SpeciesViewHolder>() {

    class SpeciesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val specieItem: TextView = itemView.findViewById(R.id.specieItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeciesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.specie_item, parent, false)
        return SpeciesViewHolder(view)
    }

    override fun getItemCount(): Int = species.size
    override fun onBindViewHolder(holder: SpeciesViewHolder, position: Int) {
        val specie = species[position]
        holder.specieItem.text = specie.name


        val context: Context = holder.specieItem.context
        val id = context.resources.getIdentifier(specie.image, "drawable", context.packageName)


        //val id: Int = Context.getResources().getIdentifier(specie.image, "drawable", null)
        holder.specieItem.setBackgroundResource(id)

    }

    fun refreshData(newSpecies: List<Specie>){
        species = newSpecies
    }

}