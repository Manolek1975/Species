package com.delek.species.activities


import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.Dialog
import com.delek.species.DrawStars
import com.delek.species.R
import com.delek.species.database.dao.StarDAO
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Ship
import com.delek.species.database.dataclass.Specie
import com.delek.species.database.dataclass.Star
import com.delek.species.database.dataclass.Tech
import com.delek.species.database.helper.DBHelper
import com.delek.species.databinding.ActivitySectorBinding
import kotlin.random.Random


class SectorActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySectorBinding
    private lateinit var db: DBHelper
    private lateinit var specie: Specie
    private lateinit var stars: StarDAO

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        specie = intent.getSerializableExtra("specie") as Specie
        val drawStars = DrawStars(this)
        setContentView(drawStars)

        db = DBHelper(this)
        stars = StarDAO(this)
        if(db.isEmpty("stars")) {
            loadStars()
            loadPlanets()
            loadBuilds()
            loadTechs()
            loadShips()
        }

        val origin = stars.getStarById(specie.origin)
        stars.setStarExplored(origin.id) // Set origin star Explored

        val i = Intent(this, MainActivity::class.java)
        var backTime = 0L
        onBackPressedDispatcher.addCallback(this) {
            if (backTime + 2000 > System.currentTimeMillis()) {
                startActivity(i)
            } else {
                Toast.makeText(this@SectorActivity, "Pulsa de nuevo para salir", Toast.LENGTH_SHORT).show()
            }
            backTime = System.currentTimeMillis()
        }
    }

    private fun loadShips() {
        val res = this.getResources()
        val name = res.getStringArray(R.array.name_ships)
        val specie = res.getStringArray(R.array.specie_ships)

        for (i in name.indices){
            val ship = Ship(0, name[i], specie[i].toInt(), 0, 0, 0)
            db.insertShips(ship)
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
            db.insertTechs(tech)
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
            db.insertBuilds(build)
        }
    }

    private fun loadPlanets() {
        val star = stars.getAllStars()
        var rnd: Int
        for (i in star){
            if (stars.getStarOrigin(i.id))
                rnd = 3
            else
                rnd = (1..8).random()

            for (j in 1..rnd){
                val image = getPlanetImage(j)
                val planet = Planet(0, i.id, i.name +" "+ j, image,0, 0, 0,
                    0, 0,0,0,0)
                db.insertPlanets(planet)
            }
        }
    }

    private fun loadStars(){
        val res = this.resources
        val id = res.getStringArray(R.array.id_stars)
        val name = res.getStringArray(R.array.name_stars)
        val image = res.getStringArray(R.array.image_stars)
        val type = res.getStringArray(R.array.type_stars)
        val coords = getCoords()
        for (i in id.indices){
            val star = Star(id[i].toInt(), name[i], image[i], "CENTAURI", 0,
                coords[i].x, coords[i].y, type[i].toInt(), 0)
            db.insertStars(star)
        }
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(this)
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if(tutorial == 1){
            dialog.showTutorial(1)
        }
    }

    override fun onPause(){
        super.onPause()
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        val edit = data.edit()
        edit.putInt("specieID", specie.id)
        edit.putInt("turn", 1)
        if(tutorial == 1) edit.putInt("tutorial", 2)
        edit.apply()
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
    private fun getCoords(): List<Point> {
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


