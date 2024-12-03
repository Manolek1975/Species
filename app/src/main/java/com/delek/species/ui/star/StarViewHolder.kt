package com.delek.species.ui.star

import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.core.Game
import com.delek.species.database.model.Star
import com.delek.species.databinding.ItemStarBinding
import com.delek.species.ui.activities.SidebarActivity

class StarViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemStarBinding.bind(itemView)

    fun render(star: Star) {
        val context = binding.root.context
        val id = Game.getResId(star.image, R.drawable::class.java)
        binding.starItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        binding.starItem.compoundDrawablePadding = 50
        binding.starItem.text = star.name

        binding.starItem.setOnClickListener {
            (context as SidebarActivity).findNavController(R.id.nav_host).navigate(
                StarFragmentDirections.actionNavStarsToNavSystem(star.id)
            )
        }
    }

}