package com.delek.species.ui.system

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.core.Dialog
import com.delek.species.core.Game
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.databinding.FragmentSystemBinding
import com.delek.species.ui.activities.SidebarActivity


class SystemFragment : Fragment() {

    private var _binding: FragmentSystemBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SystemAdapter
    private val args: SystemFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSystemBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()
        val star = StarDAO(context).getStarById(args.starId)
        val id = Game.getResId(star.image, R.drawable::class.java)
        binding.starInfo.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        binding.starInfo.text = star.name

        if (star.explore != -1) { // TODO Cambiar a 0 para ocultar planetas
            adapter = SystemAdapter(PlanetDAO(context).getPlanetsByStarId(star.id))
            binding.systemRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.systemRecyclerView.adapter = adapter
            binding.explored.visibility = View.GONE
        }

        binding.starInfo.setOnClickListener {
            (activity as SidebarActivity).openDrawer()
        }

        binding.explored.setOnClickListener{
            val navController = findNavController()
            navController.popBackStack()
        }
        tutorial()
        return root
    }

    private fun tutorial() {
        val dialog = Dialog(requireContext())
        val data = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
        var tutorial = data.getInt("tutorial", 0)
        if(tutorial == 2){
            tutorial += 1
            data.edit().putInt("tutorial", tutorial).apply()
            dialog.showTutorial(2)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}