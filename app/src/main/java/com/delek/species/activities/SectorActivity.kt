package com.delek.species.activities


import android.graphics.Point
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.DrawStars
import com.delek.species.R
import com.delek.species.database.DBHelper
import com.delek.species.database.Planet
import com.delek.species.database.Star
import com.delek.species.databinding.ActivitySectorBinding
import kotlin.random.Random


class SectorActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySectorBinding
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()
        val drawStars = DrawStars(this)
        setContentView(drawStars)

        db = DBHelper(this)
        if(db.isEmpty("stars")) {
            loadStars()
            loadPlanets()
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

    private fun loadPlanets() {
        val star = db.getAllStars()
        for (i in star){
            val planets = (1..8).random()
            for (j in 1..planets){
                val image = getPlanetImage(j)
                val planet = Planet(0, i.id, i.name +" "+ j, image,0, 0, 0,
                    0, 0,0,0,0)
                db.insertPlanets(planet)
            }
        }
    }

    fun getPlanetImage(j: Int): String {
        when (j) {
            1 -> return "planet1"
            2 -> return "planet2"
            3 -> return "planet3"
            4 -> return "planet4"
            5 -> return "planet5"
            6 -> return "planet6"
            7 -> return "planet7"
            8 -> return "planet8"
        }
        return "planet1"
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

    fun hideSystemBars() {
        enableEdgeToEdge()
        val controller = WindowInsetsControllerCompat(
            window, window.decorView
        )
        controller.hide(WindowInsetsCompat.Type.systemBars())
        controller.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    }

}


