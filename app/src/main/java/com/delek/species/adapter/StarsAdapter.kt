package com.delek.species.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.activities.SidebarActivity
import com.delek.species.database.model.Star
import com.delek.species.model.Game
import com.google.android.material.navigation.NavigationView

class StarsAdapter(private var stars: List<Star>,
                   private val context: Context):
    RecyclerView.Adapter<StarsAdapter.StarViewHolder>() {

    class StarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val starItem: TextView = itemView.findViewById(R.id.starItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_star, parent, false)
        return StarViewHolder(view)
    }

    override fun onBindViewHolder(holder: StarViewHolder, position: Int) {
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val star = stars[position]
        holder.starItem.text = star.name
        //holder.starItem.text = star.sector.toString()
        val id = Game.getResId(star.image, R.drawable::class.java)
        holder.starItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.starItem.compoundDrawablePadding = 50

        holder.starItem.setOnClickListener{
            data.edit().putInt("star", star.id).apply()
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(8) // To System
            val navController = (context).findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }
    }
    override fun getItemCount(): Int = stars.size

}