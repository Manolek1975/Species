package com.delek.species.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View.VISIBLE
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.game.Dialog
import com.delek.species.R
import com.delek.species.adapter.PlanetBuildsAdapter
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet
import com.delek.species.databinding.ActivityPlanetBinding


class PlanetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlanetBinding
    private lateinit var adapter: PlanetBuildsAdapter
    private lateinit var planetDao: PlanetDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        planetDao = PlanetDAO(this)
        val build = intent.getSerializableExtra("build") as Build?
        val data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specie = data.getInt("specie", 0)
        val planetId = data.getInt("planet", 0)
        val planet = planetDao.getPlanetById(planetId)

        // Planet Info
        val planetID = resources.getIdentifier(planet.image, "drawable", packageName)
        binding.planetInfo.setCompoundDrawablesWithIntrinsicBounds(planetID, 0, 0, 0)
        binding.planetInfo.text = planet.name

        // Ship Info
        //TODO comprobar todas las naves en orbita
        val ships = ShipDAO(this).getShipsByPlanet(planet.position)
        for (ship in ships){
            val shipID = resources.getIdentifier(ship.image, "drawable", packageName)
            if (specie == ship.specieId){
                binding.shipInfo.setImageResource(shipID)
                data.edit().putInt("ship", ship.id).apply()

                binding.shipInfo.setOnClickListener {
                    val i = Intent(this, ShipDevicesActivity::class.java)
                    startActivity(i)
                }
            }


        }

        // Build Info
        if (build != null) {
            val planetBuild = planetDao.getPlanetBuild(build, planet)
            if (planetBuild.id != 0) planetDao.setPlanetBuild(planetBuild)
            else planetDao.insertPlanetBuild(build, planet)
            println("Level: " + planetBuild.level.toString())
        }

        val planetBuilds = planetDao.getAllPlanetBuilds(planet)
        val builds = BuildDAO(this).getBuildsByPlanet(planetBuilds)
        adapter = PlanetBuildsAdapter(builds, planetDao, planet, this)
        binding.planetBuildsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.planetBuildsRecyclerView.adapter = adapter

        // Manage FAB and Textview Message
        when (planet.explore) {
            0 -> binding.explored.visibility = VISIBLE
            1 -> {
                binding.planetType.text = setType(planet.type)
            }
            2 -> {
                binding.fab.visibility = VISIBLE
                binding.planetType.text = setType(planet.type)
                setResources(planet)
            }
        }

        // FAB
        binding.fab.setOnClickListener { _ ->
            val i = Intent(this, BuildActivity::class.java)
            i.putExtra("planet", planet)
            startActivity(i)
        }

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
        val dialog = Dialog(this)
        val data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if(tutorial == 3) dialog.showTutorial(3)
        if(tutorial == 5) dialog.showTutorial(5)
        if(tutorial == 7) dialog.showTutorial(7)
        if(tutorial == 9) dialog.showTutorial(9)

    }

    override fun onPause(){
        super.onPause()
        val data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        val edit = data.edit()
        if(tutorial == 3) edit.putInt("tutorial", 4)
        if(tutorial == 5) edit.putInt("tutorial", 6)
        if(tutorial == 7) edit.putInt("tutorial", 8)
        if(tutorial == 9) edit.putInt("tutorial", 10)
        edit.apply()
    }

    private fun hideSystemBars() {
        enableEdgeToEdge()
        val controller = WindowInsetsControllerCompat(
            window, window.decorView
        )
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }
}