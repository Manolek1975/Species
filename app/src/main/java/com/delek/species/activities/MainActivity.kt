package com.delek.species.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.R
import com.delek.species.database.dataclass.Specie
import com.delek.species.database.helper.DBHelper
import com.delek.species.databinding.ActivityMainBinding


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
            if(db.isEmpty("species")) loadSpecies()
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

    // Load Species resources from species.xml
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
            db.insertSpecies(specie)
            finish()
        }
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




