package com.delek.species.ui.tech

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
import com.delek.species.core.Dialog
import com.delek.species.database.dao.TechDAO
import com.delek.species.databinding.FragmentTechBinding
import com.delek.species.ui.activities.SidebarActivity


class TechFragment : Fragment() {

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
        tutorial()
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

    private fun tutorial() {
        val dialog = Dialog(requireContext())
        val data = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
        var tutorial = data.getInt("tutorial", 0)
        val list = listOf(10, 21, 25, 26, 27)
        if (list.contains(tutorial)) {
            dialog.showTutorial(tutorial)
            tutorial += 1
            data.edit().putInt("tutorial", tutorial).apply()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}