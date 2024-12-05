package com.delek.species.ui.crono

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.delek.species.R
import com.delek.species.ui.activities.SidebarActivity
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.PlanetBuildsDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ProdDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.database.dao.TechDAO
import com.delek.species.database.model.Prod
import com.delek.species.databinding.FragmentCronoBinding
import com.delek.species.core.Dialog
import com.delek.species.core.Game
import com.delek.species.database.model.Ship


class CronoFragment: Fragment() {

    private var _binding: FragmentCronoBinding? = null
    //private lateinit var adapter: CronoAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCronoBinding.inflate(inflater, container, false)
        val root: View = binding.root
tutorial()
        // Header
        val res = ResourcesCompat.getDrawable(resources, android.R.drawable.ic_media_ff, null)
        binding.hipercronoHeader.setCompoundDrawablesWithIntrinsicBounds(res, null, null, null)
        binding.hipercronoHeader.text = getString(R.string.hipercrono)
        binding.fechaHeader.text = getString(R.string.fecha_estelar_text)

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        var year = data.getInt("year", 0)
        var day = data.getInt("day", 0)
        val minProd = ProdDAO(context).getMinProd()
        var min: Long = minProd.days.toLong()

        // Planets without production
        val noProd = PlanetDAO(context).getNoProd(specieId)
        val exist = ProdDAO(context).isProd()
        val specie = SpecieDAO(context).getSpecieById(specieId)
        println(noProd)
        if (exist){
            min = 0
            val id = Game.getResId(noProd[0].image, R.drawable::class.java)
            binding.noProd.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
            binding.noProd.setTextColor(Color.parseColor(specie.color))
            binding.noProd.text = noProd[0].name
            binding.noProdMessage.visibility = View.VISIBLE
            binding.noProd.setOnClickListener {
                (context as SidebarActivity).findNavController(R.id.nav_host).navigate(
                    CronoFragmentDirections.actionNavHipercronoToNavSurface(noProd[0].id)
                )
            }
        }
        //TODO Perhaps list of build, ships or tech under construction?

        //Advance crono
        if (min > 0) {
            val timer = object : CountDownTimer((min + 1) * 100, 100) {
                override fun onTick(millisUntilFinished: Long) {
                    //TODO Update Species IA
                    //Estelar Date: 365 days = 1 year
                    ++day
                    if (day > 365) {
                        ++year
                        day = 0
                    }
                    //Decrement days left on all items
                    val prodList = ProdDAO(context).getALLProd()
                    for (prod in prodList) {
                        ProdDAO(context).decrementDays(prod)
                    }
                    //Show Estelar date
                    binding.fechaEstelar.text = buildString {
                        append(year)
                        append(".")
                        append(day)
                    }
                }

                override fun onFinish() {
                    data.edit().putInt("year", year).apply()
                    data.edit().putInt("day", day).apply()
                    val planet = PlanetDAO(context).getPlanetById(minProd.planet)
                    //Show Dialog if days are finished
                    if (minProd.type == 1) {
                        val build = BuildDAO(context).getBuildById(minProd.typeId)
                        //val planet = PlanetDAO(context).getPlanetById(minProd.planet)
                        val planetBuild = PlanetBuildsDAO(context).getPlanetBuildById(build.id, planet)
                        if (planetBuild.id != 0) PlanetBuildsDAO(context).setBuildLevel(planetBuild)
                        else PlanetBuildsDAO(context).insertPlanetBuild(build, planet)
                        //TODO Decrement population
                        println("Build=$build")
                        updatePlanetResources(context, minProd)
                        Dialog(context).buildDone(build, planet)
                    } else if (minProd.type == 2) {
                        //if (minProd.typeId >= 0){
                            val ship = Ship(0, minProd.name, specie.imgShip, specie.id, minProd.planet, 0 )
                            ShipDAO(context).insertShips(ship)
                            //TODO insertar ship devices
                            Dialog(context).shipDone(ship, planet)
/*                        } else {
                            val ship = ShipDAO(context).getShipById(minProd.typeId)
                            println("Ship=$ship")
                            Dialog(context).shipJourney(ship)
                        }*/
                    } else if (minProd.type == 3 && minProd.typeId != 0) {
                        val learned = TechDAO(context).getTechLearned(minProd.typeId)
                        TechDAO(context).setLearned(learned)
                        val tech = TechDAO(context).getTechById(minProd.typeId)
                        TechDAO(context).insertTechsLearned(specieId, tech.unlock)
                        println("Tech=$tech")
                        Dialog(context).techDone(tech)
                    }
                    ProdDAO(context).deleteProd(minProd.id)
                }
            }
            timer.start()
        }

        binding.fechaEstelar.text = buildString {
            append(year)
            append(".")
            append(day)
        }

        binding.hipercronoHeader.setOnClickListener {
            (activity as SidebarActivity).openDrawer()
        }
        //TODO Perhaps list of build, ships or tech under construction?
/*        adapter = BuildsAdapter(BuildDAO(context).getBuildsByTech(tech), planet, context)
        binding.buildsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.buildsRecyclerView.adapter = adapter*/

        return root
    }

    private fun tutorial() {
        val dialog = Dialog(requireContext())
        val data = requireContext().getSharedPreferences("data", Context.MODE_PRIVATE)
        var tutorial = data.getInt("tutorial", 0)
        val list = listOf(11)
        if (list.contains(tutorial)) {
            dialog.showTutorial(tutorial)
            tutorial += 1
            data.edit().putInt("tutorial", tutorial).apply()
        }
    }

    private fun updatePlanetResources(context: Context, minProd: Prod) {
        val planet = PlanetDAO(context).getPlanetById(minProd.planet)
        val type = PlanetDAO(context).getType(planet.type)
        val build = BuildDAO(context).getBuildById(minProd.typeId)

        val data: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val science = data.getInt("science", 0) + build.science * type.tech
        data.edit().putInt("science", science).apply()

        val resList = mutableMapOf(
            "food" to planet.food + build.food * type.food,
            "prod" to planet.production + build.industry * type.prod,
            "res" to planet.research + build.science * type.tech,
            "def" to planet.defense + build.defense,
            "pop" to planet.population + build.population
        )

        PlanetDAO(context).setPlanetResources(minProd.planet, resList)
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 11) dialog.showTutorial(11)
    }

    override fun onPause(){
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 11) data.edit().putInt("tutorial", 12).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}