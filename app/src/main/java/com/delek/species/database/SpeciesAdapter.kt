package com.delek.species.database

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.DialogActivity
import com.delek.species.R


class SpeciesAdapter(private var species: List<Specie>,
                     context: Context):
    RecyclerView.Adapter<SpeciesAdapter.SpecieViewHolder>() {

    class SpecieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val specieItem: TextView = itemView.findViewById(R.id.specieItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpecieViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.specie_item, parent, false)
        return SpecieViewHolder(view)
    }

    override fun onBindViewHolder(holder: SpecieViewHolder, position: Int) {
        val specie = species[position]
        holder.specieItem.text = specie.name
        val context: Context = holder.specieItem.context
        val id = context.resources.getIdentifier(specie.image, "drawable", context.packageName)
        holder.specieItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.specieItem.setCompoundDrawablePadding(50)

        holder.specieItem.setOnClickListener{
            val intent = Intent(holder.itemView.context, DialogActivity::class.java).apply {
                putExtra("specie_id", specie.id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = species.size

    fun refreshData(newSpecies: List<Specie>){
        species = newSpecies
        notifyDataSetChanged()
    }

}