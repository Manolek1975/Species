package com.delek.species.ui.surface

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.ui.activities.SidebarActivity
import com.delek.species.database.model.Build
import com.delek.species.core.Game
import com.google.android.material.navigation.NavigationView


class SurfaceOrbitalAdapter(private var list: List<String>, private val context: Context):
    RecyclerView.Adapter<SurfaceOrbitalAdapter.BuildViewHolder>() {

    class BuildViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val orbitalItem: TextView = itemView.findViewById(R.id.orbitalItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuildViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_orbital, parent, false)
        return BuildViewHolder(view)
    }

    override fun onBindViewHolder(holder: BuildViewHolder, position: Int) {
        val list = list[position]
        val resources = context.resources
        val id = Game.getResId(list, R.drawable::class.java)
        val res = ResourcesCompat.getDrawable(resources, id, null)
        val bitmap = res?.toBitmap(70, 60)
        val scale = bitmap?.toDrawable(Resources.getSystem())
        holder.orbitalItem.setCompoundDrawablesWithIntrinsicBounds(scale, null, null, null)
        holder.orbitalItem.compoundDrawablePadding = 50

        holder.orbitalItem.setOnClickListener {
            if (list.first() == 'b') {
                val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
                val item = nv.menu.getItem(12) // To Shipyard
                val navController = (context).findNavController(R.id.nav_host)
                NavigationUI.onNavDestinationSelected(item, navController)
            } else {

                (context as SidebarActivity).findNavController(R.id.nav_host).navigate(
                    SurfaceFragmentDirections.actionNavSurfaceToNavSpace()
                )

/*                val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
                val item = nv.menu.getItem(13) // To Space
                val navController = (context).findNavController(R.id.nav_host)
                NavigationUI.onNavDestinationSelected(item, navController)*/
            }
        }
    }

    override fun getItemCount(): Int = list.size

}