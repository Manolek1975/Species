package com.delek.species.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.model.Dialog
import com.delek.species.R
import com.delek.species.database.dataclass.Tech

class TechsAdapter(private var tech: List<Tech>,
                   private val context: Context):
    RecyclerView.Adapter<TechsAdapter.TechViewHolder>() {

    class TechViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val techItem: TextView = itemView.findViewById(R.id.techItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tech_item, parent, false)
        return TechViewHolder(view)
    }

    override fun onBindViewHolder(holder: TechViewHolder, position: Int) {
        val dialog = Dialog(context)
        val tech = tech[position]
        holder.techItem.text = tech.name

        holder.techItem.setOnClickListener{
            //dialog.showBuild(build, planet)
        }
    }

    override fun getItemCount(): Int = tech.size

}