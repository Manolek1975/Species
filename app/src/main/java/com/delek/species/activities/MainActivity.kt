package com.delek.species.activities

import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.R
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dao.DeviceDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dao.ShipDevicesDAO
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.database.dao.TechDAO
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Device
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Ship
import com.delek.species.database.dataclass.ShipDevices
import com.delek.species.database.dataclass.Specie
import com.delek.species.database.dataclass.Star
import com.delek.species.database.dataclass.Tech
import com.delek.species.database.helper.DBHelper
import com.delek.species.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)
        binding.playButton.setOnClickListener {
            if(db.isEmpty("species")) {
                Toast.makeText(this, "Crando Galaxia...", Toast.LENGTH_LONG).show()
                loadTables()
            }
            val i = Intent(this, SpecieActivity::class.java)
            startActivity(i)
        }

        binding.optionsButton.setOnClickListener {
            val i = Intent(this, OptionsActivity::class.java)
            startActivity(i)
        }

        onBackPressedDispatcher.addCallback(this) {
            finishAffinity()
        }
    }

    private fun loadTables() {
        loadSpecies()
        loadStarsSector1()
        loadStarsSector2()
        loadPlanets()
        loadBuilds()
        loadTechs()
        loadShips()
        loadDevices()
        loadShipDevices()
    }

    // Load resources from xml files to database
    private fun loadSpecies(){
        val res = this.resources
        val name = res.getStringArray(R.array.name_species)
        val image = res.getStringArray(R.array.image_species)
        val desc = res.getStringArray(R.array.description_species)
        val star = res.getStringArray(R.array.origin_species)
        val color = res.getStringArray(R.array.color_species)
        val origin = res.getStringArray(R.array.origin_species)

        for (i in name.indices){
            val specie = Specie(0, name[i], desc[i], image[i], type = 0, skill = "",
                star = star[i].toInt(), color = color[i], origin = origin[i].toInt())
            SpecieDAO(this).insertSpecies(specie)
            finish()
        }
    }

    private fun loadStarsSector1(){
        val res = this.resources
        val name = res.getStringArray(R.array.name_stars_s1)
        val image = res.getStringArray(R.array.image_stars_s1)
        val type = res.getStringArray(R.array.type_stars_s1)
        val sector = 1
        val coords = getCoords()
        for (i in name.indices){
            val star = Star(0, name[i], image[i], sector,0,
                coords[i].x, coords[i].y, type[i].toInt(), 0)
            StarDAO(this).insertStars(star)
        }
    }

    private fun loadStarsSector2(){
        val res = this.resources
        val name = res.getStringArray(R.array.name_stars_s2)
        val image = res.getStringArray(R.array.image_stars_s2)
        val type = res.getStringArray(R.array.type_stars_s2)
        val sector = 2
        val coords = getCoords()
        for (i in name.indices){
            val star = Star(0, name[i], image[i], sector,0,
                coords[i].x, coords[i].y, type[i].toInt(), 0)
            StarDAO(this).insertStars(star)
        }
    }

    private fun loadPlanets() {
        val star = StarDAO(this).getAllStars()
        var rnd: Int
        for (i in star){
            if (StarDAO(this).getStarOrigin(i.id))
                rnd = 8
            else
                rnd = (1..7).random()

            for (j in 1..rnd){
                val image = getPlanetImage(j)
                val planet = Planet(0, i.id, i.name +" "+ j, image, j, setSize(j), setType(j),
                    0,0, 0,0,0,0)
                PlanetDAO(this).insertPlanets(planet)
            }
        }
    }

    private fun setSize(j: Int): Int {
        return when (j) {
            1 -> 1
            in 2..4 -> 2
            in 5..7 -> 3
            8 -> 1
            else -> 0
        }
    }

    private fun setType(j: Int): Int {
        return when (j) {
            in 1..4 -> 1
            in 5..7 -> 2
            8 -> 3
            else -> 0
        }
    }

    private fun loadBuilds() {
        val res = this.getResources()
        val name = res.getStringArray(R.array.builds_name)
        val image = res.getStringArray(R.array.builds_image)
        val description = res.getStringArray(R.array.builds_description)
        val tech = res.getStringArray(R.array.builds_tech)
        val cost = res.getStringArray(R.array.builds_cost)
        val food = res.getStringArray(R.array.builds_food)
        val industry = res.getStringArray(R.array.builds_industry)
        val science = res.getStringArray(R.array.builds_science)

        for (i in name.indices){
            val build = Build(0, name[i], description[i], image[i], tech[i].toInt(),
                cost[i].toInt(), food[i].toInt(), industry[i].toInt(), science[i].toInt(),
                0, 0, 0, 0)
            BuildDAO(this).insertBuilds(build)
        }
    }

    private fun loadTechs() {
        val res = this.getResources()
        val name = res.getStringArray(R.array.name_techs)
        val cost = res.getStringArray(R.array.cost_techs)
        val require = res.getStringArray(R.array.require_techs)
        val unlock = res.getStringArray(R.array.unlock_techs)

        for (i in name.indices){
            val tech = Tech(0, name[i], cost[i].toInt(), require[i].toInt(), unlock[i].toInt())
            TechDAO(this).insertTechs(tech)
        }
    }

    private fun loadShips() {
        val res = this.getResources()
        val name = res.getStringArray(R.array.name_ships)
        val image = res.getStringArray(R.array.image_ships)
        val specie = res.getStringArray(R.array.specie_ships)
        val orbit = res.getStringArray(R.array.orbit_ships)

        for (i in name.indices){
            val ship = Ship(0, name[i], image[i], specie[i].toInt(), orbit[i].toInt(),0, 0)
            ShipDAO(this).insertShips(ship)
        }
    }

    private fun loadDevices() {
        val res = this.getResources()
        val name = res.getStringArray(R.array.name_devices)
        val desc = res.getStringArray(R.array.desc_devices)
        val image = res.getStringArray(R.array.image_devices)
        val type = res.getStringArray(R.array.type_devices)
        val cost = res.getStringArray(R.array.cost_devices)
        val power = res.getStringArray(R.array.power_devices)
        val tech = res.getStringArray(R.array.tech_devices)

        for (i in name.indices){
            val device = Device(0, name[i], desc[i], image[i], type[i].toInt(), cost[i].toInt(), power[i].toInt(), tech[i].toInt())
            DeviceDAO(this).insertDevices(device)
        }
    }

    private fun loadShipDevices() {
        val ships = ShipDAO(this).getAllShips()
        val device = DeviceDAO(this).getAllDevices()
        for (i in ships){
            for (j in device){
                if (j.techId == 1){
                    val shipDevice = ShipDevices(0, i.id, j.id)
                    ShipDevicesDAO(this).insertShipDevices(shipDevice)
                }
            }
        }
    }

    private fun getPlanetImage(j: Int): String {
        var image = j
        val rnd = (0..1).random()
        if (rnd == 1 && j <= 4 ) image = j + 8
        when (image) {
            1 -> return "icon_planet1"
            2 -> return "icon_planet2"
            3 -> return "icon_planet3"
            4 -> return "icon_planet4"
            5 -> return "icon_planet5"
            6 -> return "icon_planet6"
            7 -> return "icon_planet7"
            8 -> return "icon_planet8"
            9 -> return "icon_planet9"
            10 -> return "icon_planet10"
            11 -> return "icon_planet11"
            12 -> return "icon_planet12"
        }
        return "planet12"
    }

    // Insert random coordinates to stars
    private fun getCoords(): MutableList<Point> {
        val random = Random
        val size = 20
        val dm = resources.displayMetrics
        val width = dm.widthPixels
        val height = dm.heightPixels
        val diameter = 200
        val radius = diameter * 0.5f
        val d2 = (diameter * diameter).toFloat()
        val coordinate : MutableList<Point> = ArrayList(size)

        val posX: MutableList<Float> = ArrayList(size)
        val posY: MutableList<Float> = ArrayList(size)
        while (posX.size < size) {
            // generate new coordinates
            val x: Float = random.nextInt(width - diameter) + radius
            val y: Float = random.nextInt(height - diameter) + radius

            System.out.printf("Generated [%3.3f, %3.3f] ... ", x, y)

            // verify it does not overlap/touch with previous circles
            var j = 0
            while (j < posX.size) {
                val dx = posX[j] - x
                val dy = posY[j] - y
                val diffSquare = (dx * dx) + (dy * dy)
                if (diffSquare <= d2) break
                ++j
            }
            // generate another pair of coordinates, if it does touch previous
            if (j != posX.size) {
                println("collided.")
                continue
            }
            println("added.")
            // not overlapping/touch, add as new circle
            posX.add(x)
            posY.add(y)
            coordinate.add(Point(x.toInt(),y.toInt()))
        }
        return coordinate
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




