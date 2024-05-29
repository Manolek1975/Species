package com.delek.species

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.database.DBHelper
import com.delek.species.database.Specie
import com.delek.species.database.SpeciesHelper
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
            loadSpecies()
            val i = Intent(this, InitialActivity::class.java)
            startActivity(i)
        }

        binding.ayudaButton.setOnClickListener {
            val i = Intent(this, InitialActivity::class.java)
            //db.deleteSpecies()
            startActivity(i)
        }
    }

    // Load Species resources from species.xml
    private fun loadSpecies(){
        val res = this.resources
        val id = res.getStringArray(R.array.id_species)
        val name = res.getStringArray(R.array.name_species)
        val image = res.getStringArray(R.array.image_species)
        val desc = res.getStringArray(R.array.description_species)
        val star = res.getStringArray(R.array.origin_species)

        for (i in name.indices){
            val specie = Specie(id[i].toInt(), name[i], desc[i], image[i], type = 0, skill = "", star = star[i].toInt())
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