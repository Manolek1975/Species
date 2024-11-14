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
import com.delek.species.adapter.TechsAdapter
import com.delek.species.database.dao.TechDAO
import com.delek.species.databinding.FragmentTechBinding
import com.delek.species.model.Dialog


class TechFragment: Fragment() {

    private var _binding: FragmentTechBinding? = null
    private lateinit var adapter: TechsAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTechBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Header
        val res = ResourcesCompat.getDrawable(resources, R.drawable.menu_tech, null)
        val bitmap = res?.toBitmap(30, 30)
        val scale = bitmap?.toDrawable(resources)
        binding.techHeader.setCompoundDrawablesWithIntrinsicBounds(scale, null, null, null)
        binding.techHeader.text = getString(R.string.menu_tech)

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val tech = TechDAO(context).getTechsBySpecie(specieId)

        adapter = TechsAdapter(tech, context)
        binding.techsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.techsRecyclerView.adapter = adapter

        binding.techHeader.setOnClickListener {
            (activity as SidebarActivity).openDrawer()
        }

        return root
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 10) dialog.showTutorial(10)
    }

    override fun onPause(){
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 10) data.edit().putInt("tutorial", 11).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}