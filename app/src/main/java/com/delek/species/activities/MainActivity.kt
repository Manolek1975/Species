package com.delek.species.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CompoundButton
import android.widget.Toast
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

        binding.ayudaButton.setOnClickListener {
            Toast.makeText(this,"PULSA JUGAR", Toast.LENGTH_SHORT).show()
        }

        binding.tutorialSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { _, isChecked ->
            val file = "game_data"
            val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
            val edit = data.edit()
            if (isChecked) {
                edit.putInt("tutorial", 1)
            } else {
                edit.putInt("tutorial", 0)
            }
            edit.apply()
        })
    }

    // Load Species resources from species.xml
    private fun loadSpecies(){
        val res = this.resources
        val id = res.getStringArray(R.array.id_species)
        val name = res.getStringArray(R.array.name_species)
        val image = res.getStringArray(R.array.image_species)
        val desc = res.getStringArray(R.array.description_species)
        val star = res.getStringArray(R.array.origin_species)
        val color = res.getStringArray(R.array.color_species)

        for (i in id.indices){
            val specie = Specie(id[i].toInt(), name[i], desc[i], image[i], type = 0, skill = "",
                star = star[i].toInt(), color = color[i])
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




