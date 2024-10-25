package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.adapter.ShipsAdapter
import com.delek.species.database.dao.ShipDAO
import com.delek.species.databinding.FragmentShipBinding
import com.delek.species.model.Dialog


class ShipFragment: Fragment() {

    private var _binding: FragmentShipBinding? = null
    private lateinit var adapter: ShipsAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val ship = ShipDAO(context).getShipsBySpecie(specieId)
        adapter = ShipsAdapter(ship, context)
        binding.shipsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.shipsRecyclerView.adapter = adapter

        return root
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 9) dialog.showTutorial(9)
    }

    override fun onPause(){
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 9) data.edit().putInt("tutorial", 10).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}