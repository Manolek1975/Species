package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.adapter.StarsAdapter
import com.delek.species.database.dao.StarDAO
import com.delek.species.databinding.FragmentStarsBinding

class StarFragment: Fragment() {

    private lateinit var _binding: FragmentStarsBinding
    private lateinit var adapter: StarsAdapter
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStarsBinding.inflate(inflater, container, false)

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val stars = StarDAO(context).getStarsExploredBySpecie(specieId)

        adapter = StarsAdapter(stars, context)
        binding.starRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.starRecyclerView.adapter = adapter

        return binding.root
    }

}