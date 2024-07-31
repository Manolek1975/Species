package com.delek.species.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.Dialog
import com.delek.species.R
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet
import com.delek.species.databinding.ActivityPlanetBinding
import com.google.android.material.snackbar.Snackbar

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
        val build = intent.getSerializableExtra("build") as Build?
        println(build?.name.toString())

        /*        val name: TextView = findViewById(R.id.planetName)
        val image: ImageView = findViewById(R.id.planetImage)
        val id = resources.getIdentifier(planet?.image, "drawable", packageName)
        name.text = planet?.name
        image.setImageResource(id)*/

        // Planet Info
        val planetInfo: TextView = findViewById(R.id.planetInfo)
        val planetID = resources.getIdentifier(planet?.image, "drawable", packageName)
        planetInfo.setCompoundDrawablesWithIntrinsicBounds(planetID, 0, 0, 0)
        planetInfo.text = planet?.name

        // Resources
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

        if (build != null) {
            val buildInfo: TextView = findViewById(R.id.buildInfo)
            val buildId = resources.getIdentifier(build.image, "drawable", packageName)
            buildInfo.setCompoundDrawablesWithIntrinsicBounds(buildId, 0, 0, 0)
            buildInfo.text = build.name
        }

        val fab: View = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            val i = Intent(this, BuildActivity::class.java)
            i.putExtra("planet", planet)
            startActivity(i)
            Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .show()
        }
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(this)
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if(tutorial == 3){
            dialog.showTutorial(R.string.tutorial_planet)
        }
        else if(tutorial == 5){
            dialog.showTutorial(R.string.tutorial_upgrade)
        }
    }

    override fun onPause(){
        super.onPause()
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val edit = data.edit()
        edit.putInt("tutorial", 4)
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