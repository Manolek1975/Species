package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.delek.species.R
import com.delek.species.activities.SidebarActivity
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.PlanetBuildsDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ProdDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dataclass.Prod
import com.delek.species.databinding.FragmentCronoBinding
import com.delek.species.model.Dialog


class CronoFragment: Fragment() {

    private var _binding: FragmentCronoBinding? = null
    //private lateinit var adapter: CronosAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCronoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Header
        val res = ResourcesCompat.getDrawable(resources, android.R.drawable.ic_media_ff, null)
        binding.hipercronoHeader.setCompoundDrawablesWithIntrinsicBounds(res, null, null, null)
        binding.hipercronoHeader.text = getString(R.string.hipercrono)
        binding.fechaHeader.text = getString(R.string.fecha_estelar_text)

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        var year = data.getInt("year", 0)
        var day = data.getInt("day", 0)
        val minProd = ProdDAO(context).getMinProd()
        val min: Long = minProd.days.toLong()

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
                    //TODO Update Planet Resources
                    updatePlanetResources(context, minProd)
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
                    //Show Dialog if days are finished
                    if (minProd.type == 1) {
                        val build = BuildDAO(context).getBuildById(minProd.typeId)
                        val planet = PlanetDAO(context).getPlanetById(minProd.planet)
                        val planetBuild = PlanetBuildsDAO(context).getPlanetBuildById(build.id, planet)
                        if (planetBuild.id != 0) PlanetBuildsDAO(context).setBuildLevel(planetBuild)
                        else PlanetBuildsDAO(context).insertPlanetBuild(build, planet)
                        //TODO Decrement population
                        println("Build=$build")
                        Dialog(context).buildDone(build, planet)
                    } else if (minProd.type == 2 && minProd.typeId != 0) {
                        val ship = ShipDAO(context).getShipById(minProd.typeId)
                        println("Ship=$ship")
                        Dialog(context).shipDone(ship)
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

    private fun updatePlanetResources(context: Context, minProd: Prod) {
        val planet = PlanetDAO(context).getPlanetById(minProd.planet)
        val resList = mutableMapOf(
            "food" to planet.food,
            "prod" to planet.production,
            "res" to planet.research,
            "def" to planet.defense,
            "pop" to planet.population
        )
        val planetBuildList = PlanetBuildsDAO(context).getPlanetBuildsByPlanet(minProd.planet)

        for (res in planetBuildList) {
            when (res.buildId) {
                2 -> resList["prod"] = resList["prod"]!! + 1
                3 -> resList["food"] = resList["food"]!! + 1
                4 -> resList["res"] = resList["res"]!! + 1
            }
        }
        val food = planet.food.rem(100) + 1
        if (food == 50)
            resList["pop"] = resList["pop"]!! + 1
        PlanetDAO(context).setPlanetResources(minProd.planet, resList)
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 8) dialog.showTutorial(8)
    }

    override fun onPause(){
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 8) data.edit().putInt("tutorial", 9).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}