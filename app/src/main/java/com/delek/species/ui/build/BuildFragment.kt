package com.delek.species.ui.build

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.ui.activities.SidebarActivity
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.databinding.FragmentBuildBinding
import com.delek.species.core.Dialog


class BuildFragment: Fragment() {

    private var _binding: FragmentBuildBinding? = null
    private lateinit var adapter: BuildsAdapter
    private val binding get() = _binding!!
    private val args: BuildFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuildBinding.inflate(inflater, container, false)
        val root: View = binding.root
        tutorial()

        val context = requireContext()
        //val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        //val planetId = data.getInt("planet", 0)
        //val tech = data.getInt("tech", 0)
        val planet = PlanetDAO(context).getPlanetById(args.planet)

        // Header
        val res = ResourcesCompat.getDrawable(resources, android.R.drawable.ic_menu_manage, null)
        binding.buildHeader.setCompoundDrawablesWithIntrinsicBounds(res, null, null, null)
        binding.buildHeader.text = getString(R.string.menu_builds)

        val list1 = BuildDAO(context).getInitialBuilds()
        val list2 = BuildDAO(context).getBuildsTechLearned()

        adapter = BuildsAdapter(list1 + list2, planet)
        binding.buildsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.buildsRecyclerView.adapter = adapter

        binding.buildHeader.setOnClickListener {
            (activity as SidebarActivity).openDrawer()
        }

        return root
    }

    private fun tutorial() {
        val dialog = Dialog(requireContext())
        val data = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
        var tutorial = data.getInt("tutorial", 0)
        val list = listOf(4, 7, 13, 16, 19, 23, 27)
        if(list.contains(tutorial)){
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