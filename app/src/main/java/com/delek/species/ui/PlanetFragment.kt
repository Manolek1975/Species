package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.activities.SidebarActivity
import com.delek.species.adapter.PlanetsAdapter
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.databinding.FragmentPlanetBinding
import com.delek.species.model.Dialog


class PlanetFragment : Fragment() {

    private var _binding: FragmentPlanetBinding? = null
    private lateinit var adapter: PlanetsAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Header
        val res = ResourcesCompat.getDrawable(resources, R.drawable.menu_planet, null)
        val bitmap = res?.toBitmap(30, 30)
        val scale = bitmap?.toDrawable(resources)
        binding.planetHeader.setCompoundDrawablesWithIntrinsicBounds(scale, null, null, null)
        binding.planetHeader.text = getString(R.string.menu_planet)

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specie = data.getInt("specie", 0)
        val planet = PlanetDAO(context).getPlanetsColonized(specie)
        adapter = PlanetsAdapter(planet, context)
        binding.planetRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.planetRecyclerView.adapter = adapter

        binding.planetHeader.setOnClickListener {
            (activity as SidebarActivity).openDrawer()
        }

        return root
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 8) dialog.showTutorial(8)
        if(tutorial == 18) dialog.showTutorial(18)
    }

    override fun onPause() {
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if (tutorial == 8) data.edit().putInt("tutorial", 9).apply()
        if (tutorial == 18) data.edit().putInt("tutorial", 19).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}