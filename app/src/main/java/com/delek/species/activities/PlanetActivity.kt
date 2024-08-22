package com.delek.species.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.Dialog
import com.delek.species.R
import com.delek.species.database.adapter.PlanetBuildsAdapter
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.helper.DBHelper
import com.delek.species.databinding.ActivityPlanetBinding


class PlanetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlanetBinding
    private lateinit var adapter: PlanetBuildsAdapter
    private lateinit var db: DBHelper
    private lateinit var planetDao: PlanetDAO
    private lateinit var builds: BuildDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        db = DBHelper(this)
        planetDao = PlanetDAO(this)
        builds = BuildDAO(this)

        var planet = intent.getSerializableExtra("planet") as Planet?
        val build = intent.getSerializableExtra("build") as Build?
        println("Planet: " + planet?.id.toString())
        println("Build: " + build?.name)
        println("Explored: " + planet?.explore.toString())

        // Planet Info
        val planetInfo: TextView = findViewById(R.id.planetInfo)
        val planetID = resources.getIdentifier(planet?.image, "drawable", packageName)
        planetInfo.setCompoundDrawablesWithIntrinsicBounds(planetID, 0, 0, 0)
        planetInfo.text = planet?.name

        // TODO("Sustituir cuando una nave entre en el planeta")
        if (planet?.id == 3 && planet.explore == 0) {
            planetDao.setPlanetExplored(planet.id)
        }
        planet = planetDao.getPlanetById(planet?.id)
        println("Planet2: " + planet.id.toString())
        println("Explored2: " + planet.explore.toString())

        if (build != null) {
            val planetBuild = planetDao.getPlanetBuild(build, planet)
            if (planetBuild.id != 0) planetDao.setPlanetBuild(planetBuild)
            else db.insertPlanetBuild(build, planet)
            println("Level: " + planetBuild.level.toString())
        }

        val planetBuilds = planetDao.getAllPlanetBuilds(planet)
        adapter = PlanetBuildsAdapter(builds.getBuildsByPlanet(planetBuilds), planetDao, planet, this)
        binding.planetBuildsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.planetBuildsRecyclerView.adapter = adapter

        // Manage FAB and Textview Message
        val fab: View = findViewById(R.id.fab)
        val explored: TextView = findViewById(R.id.explored)
        when (planet.explore) {
            0 -> fab.visibility = GONE
            1 -> explored.text = getString(R.string.fundar_colonia)
            2 -> {
                explored.visibility = GONE
                setResources(planet)
            }
        }

        fab.setOnClickListener { _ ->
            if (planet.explore == 1) {
                // TODO("Comprobar si la nave tiene modulo de colonización")
                val dialog = Dialog(this)
                planetDao.setPlanetColonized(planet.id)
                explored.visibility = GONE
                dialog.showColony(planet)
            } else if (planet.explore == 2) {
                val i = Intent(this, BuildActivity::class.java)
                i.putExtra("planet", planet)
                startActivity(i)
            }
        }
    }


    private fun setResources(planet: Planet) {
        val foodInfo: TextView = findViewById(R.id.foodInfo)
        foodInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos1, 0, 0)
        foodInfo.text = planet.food.toString()

        val prodInfo: TextView = findViewById(R.id.prodInfo)
        prodInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos2, 0, 0)
        prodInfo.text = planet.production.toString()

        val scienceInfo: TextView = findViewById(R.id.scienceInfo)
        scienceInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos3, 0, 0)
        scienceInfo.text = planet.research.toString()

        val energyInfo: TextView = findViewById(R.id.energyInfo)
        energyInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos4, 0, 0)
        energyInfo.text = planet.research.toString()

        val popInfo: TextView = findViewById(R.id.popInfo)
        popInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos5, 0, 0)
        popInfo.text = planet.population.toString()
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(this)
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if(tutorial == 3){
            dialog.showTutorial(3)
        }
        else if(tutorial == 5){
            dialog.showTutorial(5)
        }
    }

    override fun onPause(){
        super.onPause()
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        val edit = data.edit()
        if(tutorial == 3) edit.putInt("tutorial", 4)
        if(tutorial == 5) edit.putInt("tutorial", 6)
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