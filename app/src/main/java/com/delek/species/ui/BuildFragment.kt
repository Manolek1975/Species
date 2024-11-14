package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.activities.SidebarActivity
import com.delek.species.adapter.BuildsAdapter
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.databinding.FragmentBuildBinding
import com.delek.species.model.Dialog


class BuildFragment: Fragment() {

    private var _binding: FragmentBuildBinding? = null
    private lateinit var adapter: BuildsAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuildBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val planetId = data.getInt("planet", 0)
        val tech = data.getInt("tech", 0)
        val planet = PlanetDAO(context).getPlanetById(planetId)

        // Header
        val res = ResourcesCompat.getDrawable(resources, android.R.drawable.ic_menu_manage, null)
        binding.buildHeader.setCompoundDrawablesWithIntrinsicBounds(res, null, null, null)
        binding.buildHeader.text = getString(R.string.menu_builds)

        adapter = BuildsAdapter(BuildDAO(context).getBuildsByTech(tech), planet, context)
        binding.buildsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.buildsRecyclerView.adapter = adapter

        binding.buildHeader.setOnClickListener {
            (activity as SidebarActivity).openDrawer()
        }

        return root
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 4) dialog.showTutorial(4)
        if(tutorial == 7) dialog.showTutorial(7)

    }

    override fun onPause(){
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 4) data.edit().putInt("tutorial", 5).apply()
        if(tutorial == 7) data.edit().putInt("tutorial", 8).apply()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}