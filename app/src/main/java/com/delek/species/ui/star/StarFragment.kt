package com.delek.species.ui.star

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
import com.delek.species.ui.activities.SidebarActivity
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
        initUI()
        openSidebar()
        return binding.root
    }

    private fun openSidebar() {
        binding.starHeader.setOnClickListener {
            (activity as SidebarActivity).openDrawer()
        }
    }

    private fun initUI() {
        initHeader()
        initRecyclerView()
    }

    private fun initHeader() {
        val res = ResourcesCompat.getDrawable(resources, R.drawable.menu_star, null)
        val bitmap = res?.toBitmap(30, 30)
        val scale = bitmap?.toDrawable(resources)
        binding.starHeader.setCompoundDrawablesWithIntrinsicBounds(scale, null, null, null)
        binding.starHeader.text = getString(R.string.menu_stars)
    }

    private fun initRecyclerView() {
        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val stars = StarDAO(context).getStarsExploredBySpecie(specieId)
        adapter = StarsAdapter(stars)
        binding.starRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.starRecyclerView.adapter = adapter
    }
}