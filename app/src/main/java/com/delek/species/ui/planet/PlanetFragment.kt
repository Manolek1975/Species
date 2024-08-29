package com.delek.species.ui.planet

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.adapter.PlanetBuildsAdapter
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dataclass.Planet
import com.delek.species.databinding.FragmentPlanetBinding

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
        val specie = data.getInt("specie", 0)
        val planetId = data.getInt("planet", 0)
        val planet = PlanetDAO(context).getPlanetById(planetId)

        // Planet Info
        val planetID = resources.getIdentifier(planet.image, "drawable", context.packageName)
        binding.planetInfo.setCompoundDrawablesWithIntrinsicBounds(planetID, 0, 0, 0)
        binding.planetInfo.text = planet.name

        // Ship Info
        val ships = ShipDAO(context).getShipsByPlanet(planet.position)
        for (ship in ships){
            val shipID = resources.getIdentifier(ship.image, "drawable", context.packageName)
            if (specie == ship.specieId){
                binding.shipInfo.setImageResource(shipID)
                data.edit().putInt("ship", ship.id).apply()

                binding.shipInfo.setOnClickListener {
/*                    val i = Intent(this, ShipDevicesActivity::class.java)
                    startActivity(i)*/
                }
            }
        }

        // Build Info
/*        if (build != null) {
            val planetBuild = planetDao.getPlanetBuild(build, planet)
            if (planetBuild.id != 0) planetDao.setPlanetBuild(planetBuild)
            else planetDao.insertPlanetBuild(build, planet)
            println("Level: " + planetBuild.level.toString())
        }*/

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
/*            val i = Intent(this, BuildActivity::class.java)
            i.putExtra("planet", planet)
            startActivity(i)*/
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}