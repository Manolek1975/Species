package com.delek.species.ui.activities.specie

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.core.Dialog
import com.delek.species.core.Game
import com.delek.species.database.model.Specie
import com.delek.species.databinding.ItemSpecieBinding

class SpecieViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemSpecieBinding.bind(itemView)

    fun render(specie: Specie) {
        val context = binding.root.context
        val dialog = Dialog(context)
        val id = Game.getResId(specie.image, R.drawable::class.java)
        binding.specieItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        binding.specieItem.compoundDrawablePadding = 50
        binding.specieItem.text = specie.name
        binding.specieItem.setOnClickListener{
            dialog.showSpecie(specie)
        }
    }
}