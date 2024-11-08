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


class PlanetsFragment: Fragment() {

    private lateinit var _binding: FragmentPlanetBinding
    private lateinit var adapter: PlanetsAdapter
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetBinding.inflate(inflater, container, false)

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val planets = PlanetDAO(context).getPlanetsExploredBySpecie(specieId)

        // Header
        val res = ResourcesCompat.getDrawable(resources, R.drawable.menu_planet, null)
        val bitmap = res?.toBitmap(30, 30)
        val scale = bitmap?.toDrawable(resources)
        binding.planetHeader.setCompoundDrawablesWithIntrinsicBounds(scale, null, null, null)
        binding.planetHeader.text = getString(R.string.menu_planet)

        adapter = PlanetsAdapter(planets, context)
        binding.planetRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.planetRecyclerView.adapter = adapter

        binding.planetHeader.setOnClickListener {
            (activity as SidebarActivity).openDrawer()
        }

        return binding.root
    }

}