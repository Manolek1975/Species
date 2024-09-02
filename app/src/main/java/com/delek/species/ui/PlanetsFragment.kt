package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.adapter.PlanetsAdapter
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.databinding.FragmentPlanetsBinding


class PlanetsFragment: Fragment() {

    private lateinit var _binding: FragmentPlanetsBinding
    private lateinit var adapter: PlanetsAdapter
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetsBinding.inflate(inflater, container, false)

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val planets = PlanetDAO(context).getPlanetsExploredBySpecie(specieId)

        adapter = PlanetsAdapter(planets, context)
        binding.planetRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.planetRecyclerView.adapter = adapter

        return binding.root
    }

}