package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.adapter.NavigationAdapter
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.databinding.FragmentNavigationBinding
import com.delek.species.model.Dialog


class NavigationFragment : Fragment() {

    private var _binding: FragmentNavigationBinding? = null
    private lateinit var adapter: NavigationAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNavigationBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val shipId = data.getInt("ship", 0)
        val starId = data.getInt("star", 0)
        val ship = ShipDAO(context).getShipById(shipId)
        val star = StarDAO(context).getStarById(starId)
        val planet = PlanetDAO(context).getPlanetsByStarId(star.id)
        adapter = NavigationAdapter(planet, ship,context)
        binding.navigationRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.navigationRecyclerView.adapter = adapter

        return root
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 8) dialog.showTutorial(8)
    }

    override fun onPause() {
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if (tutorial == 8) data.edit().putInt("tutorial", 9).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}