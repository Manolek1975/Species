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
import com.delek.species.database.dao.DeviceDAO
import com.delek.species.database.dao.PlanetBuildsDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ProdDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dao.ShipDevicesDAO
import com.delek.species.database.dataclass.Planet
import com.delek.species.databinding.FragmentSurfaceBinding
import com.delek.species.model.Dialog
import com.delek.species.model.Game
import com.google.android.material.navigation.NavigationView


class SurfaceFragment : Fragment() {

    private var _binding: FragmentSurfaceBinding? = null
    private lateinit var adapter: PlanetBuildsAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurfaceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()
        val dialog = Dialog(requireContext())
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val planetId = data.getInt("planet", 0)

        // Planet Info
        val planet = PlanetDAO(context).getPlanetById(planetId)
        val type = PlanetDAO(context).getType(planet.type)
        val id = Game.getResId(planet.image, R.drawable::class.java)
        binding.planetInfo.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        binding.planetInfo.text = planet.name
        binding.planetType.text = getString(R.string.planet_type, type.name)

        // Ship Info
        val ships = ShipDAO(context).getShipsByPlanet(planet.id)
        var colonyModule = false
        for (ship in ships){
            val shipId = Game.getResId(ship.image, R.drawable::class.java)
            if (specieId == ship.specieId){
                binding.shipInfo.setImageResource(shipId)
                //TODO Planet explored
                val explored = PlanetDAO(context).getPlanetExplored(planetId)
                if (!explored) PlanetDAO(context).insertPlanetExplored(specieId, planetId)
                data.edit().putInt("ship", ship.id).apply()

                colonyModule = DeviceDAO(context).getColonyDevice(ship.id)

                binding.shipInfo.setOnClickListener {
                    val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
                    val item = nv.menu.getItem(10) // To Ship devices
                    val navController = context.findNavController(R.id.nav_host)
                    NavigationUI.onNavDestinationSelected(item, navController)
                }
            }
        }

        // Prod Info
        val prod = ProdDAO(context).getPlanetBuildProd(planet.id)
        if (prod.id > 0){
            val build = BuildDAO(context).getBuildById(prod.typeId)
            val prodID = Game.getResId(build.image, R.drawable::class.java)
            binding.prod.setCompoundDrawablesWithIntrinsicBounds(prodID, 0, 0, 0)
            binding.prod.text = build.name
            binding.prodDays.text = prod.days.toString()
        }

        if (prod.typeId == 0){
            binding.prod.text = getString(R.string.sin_produccion)
        }

        // Builds
        setAdapter(planetId, context)
/*        val planetBuilds = PlanetBuildsDAO(context).getPlanetBuildsByPlanet(planet.id)
        val builds = BuildDAO(context).getBuildsByPlanet(planetBuilds)
        adapter = PlanetBuildsAdapter(builds, PlanetBuildsDAO(context), planet, context)
        binding.planetBuildsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.planetBuildsRecyclerView.adapter = adapter*/

        // Manage FAB and Textview Message
        //val explored = PlanetDAO(context).getPlanetExplored(planet.id)
        val colony = PlanetDAO(context).getPlanetColony(planet.id)
/*        if (!explored) {
            binding.explored.visibility = View.VISIBLE*/
        if (planet.owner == 0 && colonyModule) {
            binding.colonyButton.visibility = View.VISIBLE
        } else if (planet.owner != 0) {
            binding.resLayout.visibility = View.VISIBLE
            binding.prodLayout.visibility = View.VISIBLE
            binding.fab.visibility = View.VISIBLE
            showResources(planet)
        }
/*        else {
            binding.explored.visibility = View.VISIBLE
            binding.explored.text = setType(planet.type)
        }*/

        binding.colonyButton.setOnClickListener{
            ShipDevicesDAO(context).removeColonyDevice(data.getInt("ship", 0), 1)
            PlanetDAO(context).setPlanetColony(planet, specieId)
            val build = BuildDAO(context).getBuildById(1) // Get colony build
            PlanetBuildsDAO(context).insertPlanetBuild(build, planet)
            binding.resLayout.visibility = View.VISIBLE
            binding.colonyButton.visibility = View.GONE
            binding.fab.visibility = View.VISIBLE
            binding.prodLayout.visibility = View.VISIBLE
            dialog.showTutorial(4)
            data.edit().putInt("tutorial", 4).apply()
/*            val p = PlanetDAO(context).getPlanetById(planetId)
            val pb = PlanetBuildsDAO(context).getPlanetBuildsByPlanet(planet.id)
            val b = BuildDAO(context).getBuildsByPlanet(pb)
            adapter = PlanetBuildsAdapter(b, PlanetBuildsDAO(context), planet, context)
            binding.planetBuildsRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.planetBuildsRecyclerView.adapter = adapter*/
            setAdapter(planetId, context)
        }

        binding.planetInfo.setOnClickListener{
            (activity as SidebarActivity).openDrawer()
        }

        binding.foodInfo.setOnClickListener {
            dialog.descFood()
        }

        binding.prodInfo.setOnClickListener {
            dialog.descProd()
        }

        binding.techInfo.setOnClickListener {
            dialog.descTech()
        }

        binding.defInfo.setOnClickListener {
            dialog.descDef()
        }

        binding.popInfo.setOnClickListener {
            dialog.descPop()
        }

        binding.prodImg.setOnClickListener {
            ProdDAO(context).deleteProd(prod.id)
            data.edit().putInt("planet", planet.id).apply()
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(7) // To Builds
            val navController = context.findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }
        // FAB
        binding.fab.setOnClickListener { _ ->
            ProdDAO(context).deleteProd(prod.id)
            data.edit().putInt("planet", planet.id).apply()
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(7) // To Builds
            val navController = context.findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }

        return root
    }

    private fun setAdapter(planetId: Int, context: Context) {
        val planet = PlanetDAO(context).getPlanetById(planetId)
        val planetBuilds = PlanetBuildsDAO(context).getPlanetBuildsByPlanet(planet.id)
        val builds = BuildDAO(context).getBuildsByPlanet(planetBuilds)
        adapter = PlanetBuildsAdapter(builds, PlanetBuildsDAO(context), planet, context)
        adapter = PlanetBuildsAdapter(builds, PlanetBuildsDAO(context), planet, context)
        binding.planetBuildsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.planetBuildsRecyclerView.adapter = adapter
        showResources(planet)
    }

    private fun setType(type: Int): CharSequence {
        when (type) {
            in 5..7 -> return "Gigante Gaseoso"
            8 -> return "Planeta Enano"
        }
        return "Planeta Terrestre"
    }

    private fun showResources(planet: Planet) {
        //var type = PlanetDAO(context!!).getType(planet.type)
        //val food = (planet.food + 1)* type.food
        binding.foodInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos1, 0, 0)
        binding.foodInfo.text = planet.food.toString()

        binding.prodInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos2, 0, 0)
        binding.prodInfo.text = planet.production.toString()

        binding.techInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos3, 0, 0)
        binding.techInfo.text = planet.research.toString()

        binding.defInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos4, 0, 0)
        binding.defInfo.text = planet.defense.toString()

        binding.popInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos5, 0, 0)
        binding.popInfo.text = planet.population.toString()
    }

    override fun onResume(){
        super.onResume()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val dialog = Dialog(requireContext())
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 3) dialog.showTutorial(3)
        if(tutorial == 5) dialog.showTutorial(5)
        if(tutorial == 6) dialog.showTutorial(6)
        if(tutorial == 8) dialog.showTutorial(8)
        if(tutorial == 9) dialog.showTutorial(9)

    }

    override fun onPause(){
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        data?.edit()?.putInt("build", 0)?.apply()
        if(tutorial == 3) data.edit().putInt("tutorial", 4).apply()
        if(tutorial == 5) data.edit().putInt("tutorial", 6).apply()
        if(tutorial == 6) data.edit().putInt("tutorial", 7).apply()
        if(tutorial == 8) data.edit().putInt("tutorial", 9).apply()
        if(tutorial == 9) data.edit().putInt("tutorial", 10).apply()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}