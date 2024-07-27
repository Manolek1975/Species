package com.delek.species.database.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.activities.SectorActivity
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
        val specie = species[position]
        holder.specieItem.text = specie.name
        val id = context.resources.getIdentifier(specie.image, "drawable", context.packageName)
        holder.specieItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.specieItem.compoundDrawablePadding = 50
        holder.specieItem.setOnClickListener{
            dialog(specie)
        }
    }

    override fun getItemCount(): Int = species.size

    private fun dialog(specie: Specie) {
        val id = context.resources.getIdentifier(specie.image, "drawable", context.packageName)
        val dialogBuilder = AlertDialog.Builder(context, R.style.AppTheme_AlertDialogStyle)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(specie.name)
        dialogBuilder.setMessage(specie.desc)
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            val intent = Intent(context, SectorActivity::class.java).apply {
                putExtra("specie", specie)
            }
            context.startActivity(intent)
        }
    .show()
}


}