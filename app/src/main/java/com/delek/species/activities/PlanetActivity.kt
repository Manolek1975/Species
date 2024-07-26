package com.delek.species.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.R
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.databinding.ActivityPlanetBinding

class PlanetActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlanetBinding
    private lateinit var db: PlanetDAO
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlanetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        db = PlanetDAO(this)
        val planet = intent.getSerializableExtra("planet") as Planet?

/*        val name: TextView = findViewById(R.id.planetName)
        val image: ImageView = findViewById(R.id.planetImage)
        val id = resources.getIdentifier(planet?.image, "drawable", packageName)
        name.text = planet?.name
        image.setImageResource(id)*/


        val planetInfo: TextView = findViewById(R.id.planetInfo)
        val drawableId = resources.getIdentifier(planet?.image, "drawable", packageName)
        planetInfo.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
        planetInfo.text = planet?.name

        val foodInfo: TextView = findViewById(R.id.foodInfo)
        foodInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos1, 0, 0)
        foodInfo.text = planet?.food.toString()


        val prodInfo: TextView = findViewById(R.id.prodInfo)
        prodInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos2, 0, 0)
        prodInfo.text = planet?.production.toString()

        val scienceInfo: TextView = findViewById(R.id.scienceInfo)
        scienceInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos3, 0, 0)
        scienceInfo.text = planet?.research.toString()

        val popInfo: TextView = findViewById(R.id.popInfo)
        popInfo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.recursos4, 0, 0)
        popInfo.text = planet?.population.toString()
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