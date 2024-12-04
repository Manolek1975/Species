package com.delek.species.ui.build

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.core.Dialog
import com.delek.species.core.Game
import com.delek.species.database.dao.PlanetBuildsDAO
import com.delek.species.database.model.Build
import com.delek.species.database.model.Planet
import com.delek.species.databinding.ItemBuildBinding

class BuildViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemBuildBinding.bind(view)

    fun render(build: Build, planet: Planet) {
        val context = binding.root.context
        val dialog = Dialog(context)
        val days = build.cost / planet.production

        val id = Game.getResId(build.image, R.drawable::class.java)
        binding.buildItem.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        binding.buildItem.compoundDrawablePadding = 50
        binding.buildItem.text = build.name
        binding.buildDaysLeft.text = days.toString()

        val planetBuild = PlanetBuildsDAO(context).checkIfShipyard(planet.id, build.id)
        binding.buildItem.setOnClickListener{
            if (planetBuild && build.id == 30) {
                dialog.showAlert("Ya tienes un astillero en tu planeta")
            } else {
                dialog.insertProdBuild(build, planet)
            }
        }
    }
}