package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.activities.SidebarActivity
import com.delek.species.adapter.PlanetBuildsAdapter
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dataclass.Planet
import com.delek.species.databinding.FragmentPlanetBinding
import com.delek.species.game.Dialog
import com.google.android.material.navigation.NavigationView

class PlanetFragment : Fragment() {

    private var _binding: FragmentPlanetBinding? = null
    private lateinit var adapter: PlanetBuildsAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlanetBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val planetId = data.getInt("planet", 0)
        val buildId = data.getInt("build", 0)
        val planet = PlanetDAO(context).getPlanetById(planetId)
        val build = BuildDAO(context).getBuildById(buildId)

        // Planet Info
        val planetID = resources.getIdentifier(planet.image, "drawable", context.packageName)
        binding.planetInfo.setCompoundDrawablesWithIntrinsicBounds(planetID, 0, 0, 0)
        binding.planetInfo.text = planet.name

        // Ship Info
        val ships = ShipDAO(context).getShipsByPlanet(planet.position)
        for (ship in ships){
            val shipID = resources.getIdentifier(ship.image, "drawable", context.packageName)
            if (specieId == ship.specieId){
                binding.shipInfo.setImageResource(shipID)
                data.edit().putInt("ship", ship.id).apply()

                binding.shipInfo.setOnClickListener {
                    val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
                    val item = nv.menu.getItem(3)
                    val navController = context.findNavController(R.id.nav_host)
                    NavigationUI.onNavDestinationSelected(item, navController)
                }
            }
        }

        // Build Info
        //TODO build always true
        if (build != null) {
            val planetBuild = PlanetDAO(context).getPlanetBuild(buildId, planet)
            if (planetBuild.id != 0) PlanetDAO(context).setPlanetBuild(planetBuild)
            else PlanetDAO(context).insertPlanetBuild(build, planet)
            println("Level: " + planetBuild.level.toString())
        }

        // Builds
        val planetBuilds = PlanetDAO(context).getAllPlanetBuilds(planet)
        val builds = BuildDAO(context).getBuildsByPlanet(planetBuilds)
        adapter = PlanetBuildsAdapter(builds, PlanetDAO(context), planet, context)
        binding.planetBuildsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.planetBuildsRecyclerView.adapter = adapter

        // Manage FAB and Textview Message
        when (planet.explore) {
            0 -> binding.explored.visibility = View.VISIBLE
            1 -> {
                binding.planetType.text = setType(planet.type)
            }
            2 -> {
                binding.fab.visibility = View.VISIBLE
                binding.planetType.text = setType(planet.type)
                setResources(planet)
            }
        }

        // FAB
        binding.fab.setOnClickListener { _ ->
            data.edit().putInt("planet", planet.id).apply()
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(4)
            val navController = context.findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }

        return root
    }

    private fun setType(type: Int): CharSequence {
        when (type) {
            in 1..4 -> return "Planeta Rocoso"
            in 5..7 -> return "Planeta Gaseoso"
        }
        return "Planeta Helado"
    }

    private fun setResources(planet: Planet) {
        binding.foodInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos1, 0, 0)
        binding.foodInfo.text = planet.food.toString()

        binding.prodInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos2, 0, 0)
        binding.prodInfo.text = planet.production.toString()

        binding.scienceInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos3, 0, 0)
        binding.scienceInfo.text = planet.research.toString()

        binding.energyInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos4, 0, 0)
        binding.energyInfo.text = planet.research.toString()

        binding.popInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos5, 0, 0)
        binding.popInfo.text = planet.population.toString()
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 3) dialog.showTutorial(3)
        if(tutorial == 5) dialog.showTutorial(5)
        if(tutorial == 7) dialog.showTutorial(7)
        if(tutorial == 9) dialog.showTutorial(9)
    }

    override fun onPause(){
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 3) data.edit().putInt("tutorial", 4).apply()
        if(tutorial == 5) data.edit().putInt("tutorial", 6).apply()
        if(tutorial == 7) data.edit().putInt("tutorial", 8).apply()
        if(tutorial == 9) data.edit().putInt("tutorial", 10).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}