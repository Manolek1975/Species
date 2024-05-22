package com.delek.species

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.database.DBHelper
import com.delek.species.database.Specie


class MainActivity : AppCompatActivity() {

    private lateinit var db: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        setContentView(R.layout.activity_main)

        db = DBHelper(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val play = findViewById<Button>(R.id.play_button)
        play.setOnClickListener {
            val i = Intent(
                this,
                InitialActivity::class.java
            )
            loadSpecies()
            startActivity(i)
        }

        val ayuda = findViewById<Button>(R.id.ayuda_button)
        ayuda.setOnClickListener {
            val i = Intent(
                this,
                InitialActivity::class.java
            )
            //db.onUpgrade(this)
            startActivity(i)
        }
    }

    private fun loadSpecies(){
        val res = this.resources
        val name = res.getStringArray(R.array.name_species)
        val image = res.getStringArray(R.array.image_species)
        val desc = res.getStringArray(R.array.description_species)
        val star = res.getStringArray(R.array.origin_species)

        for (i in name.indices){
            val specie = Specie(id = 0, name[i], desc[i], image[i], type = 0, skill = "", star = star[i])
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