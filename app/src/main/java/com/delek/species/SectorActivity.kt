package com.delek.species

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.database.DBHelper

import com.delek.species.database.Star
import com.delek.species.databinding.ActivitySectorBinding


class SectorActivity : AppCompatActivity() {

    private var context: Context = this
    private lateinit var binding: ActivitySectorBinding
    private lateinit var db: DBHelper

    private var specieId: Int = -1
    private lateinit var fondo: ImageView
    private lateinit var bitmap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        db = DBHelper(this)
        specieId = intent.getIntExtra("specie_id", -1)
        if (specieId == -1){
            finish()
            return
        }

        val specie = db.getSpecieById(specieId)
        binding.sector.text = specie.name

        loadStars()
        drawSector()
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
            //finish()
        }
    }

    private fun drawSector() {
        // Initializing the ImageView
        fondo = findViewById(R.id.fondoSector)

        val id = context.resources.getIdentifier("star1", "drawable", context.packageName)
        val imageStar = ContextCompat.getDrawable(context, id) as BitmapDrawable?
        if (imageStar != null) {
            bitmap = imageStar.bitmap
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, false)
        fondo.setImageBitmap(bitmap)

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


