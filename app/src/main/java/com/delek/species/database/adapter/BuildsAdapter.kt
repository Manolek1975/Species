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
import com.delek.species.activities.PlanetActivity
import com.delek.species.activities.SectorActivity
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Specie

class BuildsAdapter(private var builds: List<Build>,
                    private var planet: Planet,
                    private val context: Context):
    RecyclerView.Adapter<BuildsAdapter.BuildViewHolder>() {

    class BuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val buildItem: TextView = itemView.findViewById(R.id.buildItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.build_item, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        val build = builds[position]
        holder.buildItem.text = build.name
        val id = context.resources.getIdentifier(build.image, "drawable", context.packageName)
        holder.buildItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.buildItem.compoundDrawablePadding = 50
        holder.buildItem.setOnClickListener{
            val intent = Intent(context, PlanetActivity::class.java).apply {
                putExtra("build", build)
                putExtra("planet", planet)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = builds.size



    private fun dialog(specie: Specie) {
        val id = context.resources.getIdentifier(specie.image, "drawable", context.packageName)
        val dialogBuilder = AlertDialog.Builder(context, R.style.AppTheme_AlertDialogStyle)
        dialogBuilder.setIcon(id)
        dialogBuilder.setTitle(specie.name)
        dialogBuilder.setMessage(specie.desc)
        dialogBuilder.setNegativeButton("Rechazar") { _, _ -> }
        dialogBuilder.setPositiveButton("Aceptar") { _, _: Int ->
            val intent = Intent(context, SectorActivity::class.java).apply {
                putExtra("specie_id", specie.id)
            }
            context.startActivity(intent)
        }
    .show()
}


}