package com.delek.species.ui.system

import android.content.Context
import android.graphics.Color
import android.view.View
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.core.Game
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.database.model.Planet
import com.delek.species.databinding.ItemPlanetBinding
import com.delek.species.ui.activities.SidebarActivity

class SystemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemPlanetBinding.bind(view)

    fun render(planet: Planet) {
        val context = binding.root.context
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val ship = data.getInt("ship", 0)
        val specie = SpecieDAO(context).getSpecieById(specieId)

        val type = PlanetDAO(context).getType(planet.type)
        if (planet.owner == specieId) {
            binding.planetItem.setTextColor(Color.parseColor(specie.color))
            binding.planetType.setTextColor(Color.parseColor(specie.color))
        }

        val id = Game.getResId(planet.image, R.drawable::class.java)
        binding.planetItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        binding.planetItem.compoundDrawablePadding = 50
        binding.planetItem.text = planet.name
        binding.planetType.text = type.name

        binding.planetItem.setOnClickListener {
            if (ship == 0)
                ShipDAO(context).updateOrbitShip(planet.id, specieId)

            (context as SidebarActivity).findNavController(R.id.nav_host).navigate(
                SystemFragmentDirections.actionNavSystemToNavSurface(planet.id)
            )
        }
    }
}