package com.delek.species


import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.database.DBHelper
import com.delek.species.database.Star
import com.delek.species.databinding.ActivitySectorBinding
import kotlin.random.Random


class SectorActivity : AppCompatActivity() {

    private var context: Context = this
    private lateinit var binding: ActivitySectorBinding
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        db = DBHelper(this)
        if(db.isEmpty("stars")) loadStars()

        val drawStars = DrawStars(this)
        setContentView(drawStars)

        //drawSector()
        //randomPosition()
    }

    private fun loadStars(){
        val res = this.resources
        val id = res.getStringArray(R.array.id_stars)
        val name = res.getStringArray(R.array.name_stars)
        val image = res.getStringArray(R.array.image_stars)
        val coords = res.getStringArray(R.array.coords_stars)

        for (i in id.indices){
            val split: List<String> = coords[i].split(",")
            val x = split[0].toInt()
            val y = split[1].toInt()
            val star = Star(id[i].toInt(), name[i], image[i], "CENTAURI", 0, x, y, type = 0, true)
            db.insertStars(star)
        }
    }

    private fun drawSector() {
        // Get scale of Sreen
        val dm = resources.displayMetrics
        val fwidth = dm.density * dm.widthPixels
        val fheight = dm.density * dm.heightPixels

    }

    fun randomPosition(){
        val random = Random
        //val rnds = (0..10).random() // generated random from 0 to 10 included
        val numberOfCircle = 8
        val width = 400
        val height = 300
        val diameter = 51
        val radius = diameter * 0.5f
        val d2 = (diameter * diameter).toFloat()

        val posX: MutableList<Float> = ArrayList(numberOfCircle)
        val posY: MutableList<Float> = ArrayList(numberOfCircle)

        while (posX.size < numberOfCircle) {  // till enough generated
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
        } // while (posX.size() < numberOfCircle)
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


