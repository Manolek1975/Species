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
import com.delek.species.R
import com.delek.species.ui.activities.SidebarActivity
import com.delek.species.databinding.FragmentDiplomacyBinding
import com.delek.species.core.Dialog


class DiplomacyFragment: Fragment() {

    private var _binding: FragmentDiplomacyBinding? = null
    //private lateinit var adapter: TechsAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiplomacyBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Header
        val res = ResourcesCompat.getDrawable(resources, R.drawable.menu_diplo, null)
        val bitmap = res?.toBitmap(30, 30)
        val scale = bitmap?.toDrawable(resources)
        binding.diploHeader.setCompoundDrawablesWithIntrinsicBounds(scale, null, null, null)
        binding.diploHeader.text = getString(R.string.menu_diplomacy)

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)

/*        adapter = TechsAdapter(tech, context)
        binding.techsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.techsRecyclerView.adapter = adapter*/

        binding.diploHeader.setOnClickListener {
            (activity as SidebarActivity).openDrawer()
        }

        return root
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 21) dialog.showTutorial(21)
    }

    override fun onPause(){
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 21) data.edit().putInt("tutorial", 22).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}