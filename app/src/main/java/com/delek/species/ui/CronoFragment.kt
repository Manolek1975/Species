package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.PlanetBuildsDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ProdDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dao.TechDAO
import com.delek.species.database.helper.ProdHelper
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

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val turn = data.getInt("turn", 0)
        var fecha = 2300
        var days = turn

        //val planetBuild = PlanetBuildsDAO(context).getMinBuild()
        //val ship = ShipDAO(context).getMinShip()
        //val tech = TechDAO(context).getMinTech()
        //val minBuild = planetBuild.daysLeft.toLong()
        //val minShip = ship.days.toLong()
        //val minTech = tech.days.toLong()

        val minProd = ProdDAO(context).getMinProd()
        var min: Long = minProd.days.toLong()

        val timer = object: CountDownTimer(min * 100, 100) {
            override fun onTick(millisUntilFinished: Long) {
                //TODO Update Species IA
                //TODO Update Planet Resources
                //Estelar Date: 365 days = 1 year
                ++days
                if (days > 365) {
                    fecha++
                    days = 0
                }
                //Decrement days left on all items
                val prodList = ProdDAO(context).getALLProd()
                for (prod in prodList) {
                    ProdDAO(context).decrementDays(prod)
                }
/*                val shipList = ShipDAO(context).getShipsUnderConstruction()
                for (ship in shipList) {
                    if(ship.days > -1)
                        ShipDAO(context).decrementDays(ship)
                }*/
                //Show Estelar date
                binding.fechaEstelar.text = buildString {
                    append(fecha)
                    append(".")
                    append(days)
                }
                println("Days=$days Min=$min")
                Log.d("Prod", prodList.toString())

            }
            override fun onFinish() {
                //Show Dialog if days are finished
                if (minProd.type == 1){
                    val build = BuildDAO(context).getBuildById(minProd.typeId)
                    val planet = PlanetDAO(context).getPlanetById(minProd.planet)
                    val planetBuild = PlanetBuildsDAO(context).getPlanetBuild(build.id, planet)
                    if (planetBuild.id != 0) PlanetBuildsDAO(context).setPlanetBuild(planetBuild)
                    else PlanetBuildsDAO(context).insertPlanetBuild(build, planet)
                    ProdDAO(context).deleteProd(minProd.id)
                    println("Build=$build")
                    Dialog(context).buildDone(build, planet)
                } else if (minProd.type == 2) {
                    val ship = ShipDAO(context).getShipById(minProd.typeId)
                    println("Ship=$ship")
                    Dialog(context).shipDone(ship)
                }
            }
        }
        timer.start()

        //TODO Perhaps list of build, ships or tech under construction?
/*        adapter = BuildsAdapter(BuildDAO(context).getBuildsByTech(tech), planet, context)
        binding.buildsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.buildsRecyclerView.adapter = adapter*/

        return root
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