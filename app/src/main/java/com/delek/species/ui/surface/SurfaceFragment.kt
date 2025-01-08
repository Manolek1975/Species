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
import com.delek.species.core.Game.Companion.tutorial
import com.delek.species.database.dao.ShipDAO



class SurfaceFragment : Fragment() {

    private var _binding: FragmentSurfaceBinding? = null
    private lateinit var orbitalAdapter: SurfaceOrbitalAdapter
    private lateinit var adapter: SurfaceBuildAdapter
    private val binding get() = _binding!!

    private val args: SurfaceFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSurfaceBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val context = requireContext()
        val list = listOf(3, 5, 6, 8, 9, 12, 14, 15, 17, 18, 20, 22, 24, 28, 30, 31)
        tutorial(list, requireContext())
        initListener()

        val dialog = Dialog(context)
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)

        // Header
        val specie = SpecieDAO(context).getSpecieById(specieId)
        val planet = PlanetDAO(context).getPlanetById(args.planet)
        val type = PlanetDAO(context).getType(planet.type)
        val img = Game.getResId(planet.image, R.drawable::class.java)
        binding.headerImg.setImageResource(img)
        binding.headerName.setTextColor(Color.parseColor(specie.color))
        binding.headerType.setTextColor(Color.parseColor(specie.color))
        binding.headerName.text = planet.name
        binding.headerType.text = getString(R.string.planet_type, type.name)

        // Orbital Adapter
        val planetBuilds = PlanetBuildsDAO(context).getPlanetBuildsByPlanet(planet.id)
        val orbitalBuilds = BuildDAO(context).getOrbitalBuildsByPlanet(planetBuilds)
        val orbitalShips = ShipDAO(context).getShipsByPlanet(planet.id)
        val orbital: MutableList<String> = mutableListOf()
        orbitalBuilds.forEach {
            orbital.add(it.image)
        }
        orbitalShips.forEach {
            orbital.add(it.image)
        }

        orbitalAdapter = SurfaceOrbitalAdapter(orbital, context)
        binding.planetOrbitalRecyclerView.setHasFixedSize(true)
        binding.planetOrbitalRecyclerView.layoutManager =
            LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
        binding.planetOrbitalRecyclerView.adapter = orbitalAdapter

        // Ship Info
        //TODO Show ships in orbital
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
        adapter = SurfaceBuildAdapter(builds, PlanetBuildsDAO(context), planet, context)
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

        binding.prodLayout.setOnClickListener {
            ProdDAO(context).deleteProd(prod.id)
            (context as SidebarActivity).findNavController(R.id.nav_host).navigate(
                SurfaceFragmentDirections.actionNavSurfaceToNavBuild(args.planet, 0)
            )
        }

        return root
    }

    private fun scaleImage(prodID: Int) {
        val res = ResourcesCompat.getDrawable(resources, prodID, null)
        val bitmap = res?.toBitmap(68, 48)
        val scale = bitmap?.toDrawable(resources)
        binding.prod.setCompoundDrawablesWithIntrinsicBounds(scale, null, null, null)
    }

    private fun initListener() {
        val dialog = Dialog(requireContext())
        binding.headerImg.setOnClickListener { (activity as SidebarActivity).openDrawer() }
        binding.foodInfo.setOnClickListener { dialog.descFood() }
        binding.prodInfo.setOnClickListener { dialog.descProd() }
        binding.techInfo.setOnClickListener { dialog.descTech() }
        binding.defInfo.setOnClickListener { dialog.descDef() }
        binding.popInfo.setOnClickListener { dialog.descPop() }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}