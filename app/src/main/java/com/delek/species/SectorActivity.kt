package com.delek.species

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.database.DBSpeciesHelper
import com.delek.species.databinding.ActivitySectorBinding


class SectorActivity() : AppCompatActivity() {

    private var context: Context = this
    private lateinit var binding: ActivitySectorBinding
    private lateinit var db: DBSpeciesHelper
    private var specieId: Int = -1

    // Declaring ImageView, Bitmap, Canvas, Paint,
    // Down Coordinates and Up Coordinates
    private lateinit var fondo: ImageView
    private lateinit var bitmap: Bitmap
/*    private lateinit var canvas: Canvas
    private lateinit var paint: Paint
    private var downX = 0f
    private var downY = 0f
    private var upX = 0f
    private var upY = 0f*/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        specieId = intent.getIntExtra("specie_id", -1)
        if (specieId == -1){
            finish()
            return
        }

        db = DBSpeciesHelper(this)
        val specie = db.getSpecieById(specieId)
        binding.sector.text = specie.name


        drawSector()
    }

    private fun drawSector() {
        // Initializing the ImageView
        fondo = findViewById(R.id.fondoSector)

        val id = context.resources.getIdentifier("star1", "drawable", context.packageName)
        val imageStar = ContextCompat.getDrawable(context, id) as BitmapDrawable?
        if (imageStar != null) {
            bitmap = imageStar.bitmap
        }
        bitmap = Bitmap.createScaledBitmap(bitmap, 50, 50, false);
        fondo.setImageBitmap(bitmap)

/*

        // Getting the current window dimensions
        val currentDisplay = windowManager.currentWindowMetrics
        val dw = currentDisplay.bounds.width()
        val dh = currentDisplay.bounds.height()





        // Creating a bitmap with fetched dimensions
        bitmap = Bitmap.createBitmap(dw, dh, Bitmap.Config.ARGB_8888)

        // Storing the canvas on the bitmap
        canvas = Canvas(bitmap)



        canvas.drawBitmap(bmStar, 0, 50, null)
        //val id = context.resources.getIdentifier(specie.image, "drawable", context.packageName)
        // Initializing Paint to determine
        // stoke attributes like color and size
*/
/*        paint = Paint()
        paint.color = Color.RED
        paint.strokeWidth = 10F*//*


        // Setting the bitmap on ImageView
        fondo.setImageBitmap(bitmap)
*/

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


