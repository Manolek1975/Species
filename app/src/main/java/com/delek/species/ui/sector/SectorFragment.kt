package com.delek.species.ui.sector

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.databinding.FragmentSectorBinding
import com.delek.species.core.Dialog
import com.delek.species.core.Game
import com.delek.species.core.Game.Companion.tutorial


class SectorFragment : Fragment() {

    private var _binding: FragmentSectorBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectorBinding.inflate(inflater, container, false)
        val context = requireContext()
        val list = listOf(1)
        tutorial(list, context)

        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specie = SpecieDAO(context).getSpecieById(data.getInt("specie", 0))
        val origin = StarDAO(context).getStarById(specie.origin)
        StarDAO(context).setStarExplored(origin.id) // Set origin star Explored
        data.edit().putInt("sector", origin.sector).apply()

        val drawStars = DrawStars(context)
        return drawStars

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}