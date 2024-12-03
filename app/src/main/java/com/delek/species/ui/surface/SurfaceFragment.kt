package com.delek.species.ui.surface

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.delek.species.R
import com.delek.species.ui.activities.SidebarActivity
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.PlanetBuildsDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ProdDAO
import com.delek.species.database.dao.ShipDevicesDAO
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.database.model.Planet
import com.delek.species.databinding.FragmentSurfaceBinding
import com.delek.species.core.Dialog
import com.delek.species.core.Game
import com.google.android.material.navigation.NavigationView


class SurfaceFragment : Fragment() {

    private var _binding: FragmentSurfaceBinding? = null
    private lateinit var orbitalAdapter: PlanetOrbitalAdapter
    private lateinit var adapter: PlanetBuildsAdapter
    private val binding get() = _binding!!

    private val args: SurfaceFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurfaceBinding.inflate(inflater, container, false)
        val root: View = binding.root

        initListener()

        val context = requireContext()
        val dialog = Dialog(context)
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        //val planetId = data.getInt("planet", 0)

        // Planet Info
        val x = args.planet
        val specie = SpecieDAO(context).getSpecieById(specieId)
        val planet = PlanetDAO(context).getPlanetById(args.planet)
        val type = PlanetDAO(context).getType(planet.type)
        val id = Game.getResId(planet.image, R.drawable::class.java)
        binding.planetInfo.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        binding.planetInfo.setTextColor(Color.parseColor(specie.color))
        binding.planetInfo.text = planet.name
        binding.planetType.text = getString(R.string.planet_type, type.name)

        val planetBuilds = PlanetBuildsDAO(context).getPlanetBuildsByPlanet(planet.id)
        val orbitalBuilds = BuildDAO(context).getOrbitalBuildsByPlanet(planetBuilds)
        orbitalAdapter = PlanetOrbitalAdapter(orbitalBuilds, context)
        binding.planetOrbitalRecyclerView.setHasFixedSize(true)
        binding.planetOrbitalRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.planetOrbitalRecyclerView.adapter = orbitalAdapter

        // Ship Info
        /*        val ships = ShipDAO(context).getShipsByPlanet(planet.id)
                var colonyModule = false
                for (ship in ships){
                    val shipId = Game.getResId(ship.image, R.drawable::class.java)
                    if (specieId == ship.specieId){
                        binding.shipInfo.setImageResource(shipId)
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
                }*/

        // Prod Info
        val prod = ProdDAO(context).getPlanetProd(planet.id)
        when (prod.type) {
            1 -> {
                val build = BuildDAO(context).getBuildById(prod.typeId)
                val prodID = Game.getResId(build.image, R.drawable::class.java)
                scaleImage(prodID)
                binding.prod.text = prod.name
                binding.prodDays.text = prod.days.toString()
            }
            2 -> {
                val prodID = Game.getResId(specie.imgShip, R.drawable::class.java)
                scaleImage(prodID)
                binding.prod.text = prod.name
                binding.prodDays.text = prod.days.toString()
            }
            else -> binding.prod.text = getString(R.string.sin_produccion)
        }

        // Builds
        val builds = BuildDAO(context).getBuildsByPlanet(planetBuilds)
        adapter = PlanetBuildsAdapter(builds, PlanetBuildsDAO(context), planet, context)
        binding.planetBuildsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.planetBuildsRecyclerView.adapter = adapter

        // Check if planet is colonized
        if (planet.owner != 0) {
            binding.resLayout.visibility = View.VISIBLE
            binding.prodLayout.visibility = View.VISIBLE
            showResources(planet)
        }

        binding.colonyButton.setOnClickListener {
            ShipDevicesDAO(context).removeColonyDevice(data.getInt("ship", 0), 1)
            PlanetDAO(context).setPlanetColony(planet, specieId)
            val build = BuildDAO(context).getBuildById(1) // Get colony build
            PlanetBuildsDAO(context).insertPlanetBuild(build, planet)
            binding.resLayout.visibility = View.VISIBLE
            binding.colonyButton.visibility = View.GONE
            binding.prodLayout.visibility = View.VISIBLE

            dialog.showTutorial(4)
            data.edit().putInt("tutorial", 4).apply()
            showResources(planet)
        }

        binding.prodImg.setOnClickListener {
            ProdDAO(context).deleteProd(prod.id)
            data.edit().putInt("planet", planet.id).apply()
            val nv: NavigationView = (context as SidebarActivity).findViewById(R.id.nav_view)
            val item = nv.menu.getItem(7) // To Builds
            val navController = context.findNavController(R.id.nav_host)
            NavigationUI.onNavDestinationSelected(item, navController)
        }

        return root
    }

    private fun initListener() {
        val dialog = Dialog(requireContext())
        binding.planetInfo.setOnClickListener { (activity as SidebarActivity).openDrawer() }
        binding.foodInfo.setOnClickListener { dialog.descFood() }
        binding.prodInfo.setOnClickListener { dialog.descProd() }
        binding.techInfo.setOnClickListener { dialog.descTech() }
        binding.defInfo.setOnClickListener { dialog.descDef() }
        binding.popInfo.setOnClickListener { dialog.descPop() }
    }

    private fun scaleImage(prodID: Int) {
        val res = ResourcesCompat.getDrawable(resources, prodID, null)
        val bitmap = res?.toBitmap(68, 48)
        val scale = bitmap?.toDrawable(resources)
        binding.prod.setCompoundDrawablesWithIntrinsicBounds(scale, null, null, null)
    }

    private fun showResources(planet: Planet) {
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

    override fun onResume() {
        super.onResume()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val dialog = Dialog(requireContext())
        val tutorial = data?.getInt("tutorial", 0)
        if (tutorial == 3) dialog.showTutorial(3)
        if (tutorial == 5) dialog.showTutorial(5)
        if (tutorial == 6) dialog.showTutorial(6)
        if (tutorial == 8) dialog.showTutorial(8)
        if (tutorial == 9) dialog.showTutorial(9)
        if (tutorial == 12) dialog.showTutorial(12)
        if (tutorial == 14) dialog.showTutorial(14)
        if (tutorial == 15) dialog.showTutorial(15)
        if (tutorial == 17) dialog.showTutorial(17)
        if (tutorial == 18) dialog.showTutorial(18)
        if (tutorial == 20) dialog.showTutorial(20)
        if (tutorial == 22) dialog.showTutorial(22)
        if (tutorial == 24) dialog.showTutorial(24)
        if (tutorial == 28) dialog.showTutorial(28)

    }

    override fun onPause() {
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        data?.edit()?.putInt("build", 0)?.apply()
        if (tutorial == 3) data.edit().putInt("tutorial", 4).apply()
        if (tutorial == 5) data.edit().putInt("tutorial", 6).apply()
        if (tutorial == 6) data.edit().putInt("tutorial", 7).apply()
        if (tutorial == 8) data.edit().putInt("tutorial", 9).apply()
        if (tutorial == 9) data.edit().putInt("tutorial", 10).apply()
        if (tutorial == 12) data.edit().putInt("tutorial", 13).apply()
        if (tutorial == 14) data.edit().putInt("tutorial", 15).apply()
        if (tutorial == 15) data.edit().putInt("tutorial", 16).apply()
        if (tutorial == 16) data.edit().putInt("tutorial", 17).apply()
        if (tutorial == 17) data.edit().putInt("tutorial", 18).apply()
        if (tutorial == 18) data.edit().putInt("tutorial", 19).apply()
        if (tutorial == 20) data.edit().putInt("tutorial", 21).apply()
        if (tutorial == 22) data.edit().putInt("tutorial", 23).apply()
        if (tutorial == 24) data.edit().putInt("tutorial", 25).apply()
        if (tutorial == 26) data.edit().putInt("tutorial", 27).apply()
        if (tutorial == 28) data.edit().putInt("tutorial", 29).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}