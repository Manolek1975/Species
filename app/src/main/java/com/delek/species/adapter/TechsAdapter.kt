package com.delek.species.adapter

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.model.Dialog
import com.delek.species.R
import com.delek.species.dao.TechDAO
import com.delek.species.database.model.Tech
import com.delek.species.model.Game

class TechsAdapter(private var tech: List<Tech>,
                   private val context: Context):
    RecyclerView.Adapter<TechsAdapter.TechViewHolder>() {

    val data: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    class TechViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val techItem: TextView = itemView.findViewById(R.id.techItem)
        val techDays: TextView = itemView.findViewById(R.id.techDaysLeft)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_tech, parent, false)
        return TechViewHolder(view)
    }

    override fun onBindViewHolder(holder: TechViewHolder, position: Int) {
        val science = data.getInt("science", 0)
        val dialog = Dialog(context)
        val tech = tech[position]
        if (science == 0){
            holder.techItem.visibility = View.GONE
            holder.techDays.visibility = View.GONE
        } else {
            holder.techDays.text = (tech.cost / science).toString()
        }
        val learned = TechDAO(context).isLearned(tech.id)
        if (learned) holder.techItem.setTextColor(Color.GREEN)
        holder.techItem.text = tech.name
        val id = Game.getResId(tech.image, R.drawable::class.java)
        holder.techItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        holder.techItem.compoundDrawablePadding = 50

        holder.techItem.setOnClickListener{
            dialog.showTech(tech)
        }
    }

    override fun getItemCount(): Int = tech.size

}