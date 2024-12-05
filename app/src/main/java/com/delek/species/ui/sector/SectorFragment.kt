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


class SectorFragment : Fragment() {

    private var _binding: FragmentSectorBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSectorBinding.inflate(inflater, container, false)

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specie = SpecieDAO(context).getSpecieById(data.getInt("specie", 0))
        val origin = StarDAO(context).getStarById(specie.origin)
        StarDAO(context).setStarExplored(origin.id) // Set origin star Explored
        data.edit().putInt("sector", origin.sector).apply()

        tutorial()
        val drawStars = DrawStars(context)
        return drawStars

    }

    private fun tutorial() {
        val dialog = Dialog(requireContext())
        val data = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
        var tutorial = data.getInt("tutorial", 0)
        if(tutorial == 1){
            tutorial += 1
            data.edit().putInt("tutorial", tutorial).apply()
            dialog.showTutorial(1)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}