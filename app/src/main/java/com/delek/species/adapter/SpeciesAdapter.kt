package com.delek.species.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.model.Dialog
import com.delek.species.R
import com.delek.species.database.dataclass.Specie

class SpeciesAdapter(private var species: List<Specie>,
                     private val context: Context):
    RecyclerView.Adapter<SpeciesAdapter.SpecieViewHolder>() {

    class SpecieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val specieItem: TextView = itemView.findViewById(R.id.specieItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.specie_item, parent, false)
        return SpecieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
        val dialog = Dialog(context)
        val specie = species[position]
        holder.specieItem.text = specie.name
        val id = context.resources.getIdentifier(specie.image, "drawable", context.packageName)
        holder.specieItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.specieItem.compoundDrawablePadding = 50
        holder.specieItem.setOnClickListener{
            dialog.showSpecie(specie)
        }
    }

    override fun getItemCount(): Int = species.size



}