package com.delek.species.activities


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.Dialog
import com.delek.species.DrawStars
import com.delek.species.database.dao.StarDAO
import com.delek.species.database.dataclass.Specie
import com.delek.species.database.helper.DBHelper
import com.delek.species.databinding.ActivitySectorBinding


class SectorActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySectorBinding
    private lateinit var db: DBHelper
    private lateinit var specie: Specie
    private lateinit var stars: StarDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        db = DBHelper(this)
        stars = StarDAO(this)
        specie = intent.getSerializableExtra("specie") as Specie

        val origin = stars.getStarById(specie.origin)
        stars.setStarExplored(origin.id) // Set origin star Explored
        val data = this.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        val edit = data.edit()
        edit.putInt("sector", origin.sector)
        edit.apply()
        val drawStars = DrawStars(this)
        setContentView(drawStars)

        val i = Intent(this, MainActivity::class.java)
        var backTime = 0L
        onBackPressedDispatcher.addCallback(this) {
            if (backTime + 2000 > System.currentTimeMillis()) {
                startActivity(i)
            } else {
                Toast.makeText(this@SectorActivity, "Pulsa de nuevo para salir", Toast.LENGTH_SHORT).show()
            }
            backTime = System.currentTimeMillis()
        }
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(this)
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if(tutorial == 1){
            dialog.showTutorial(1)
        }
    }

    override fun onPause(){
        super.onPause()
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        val edit = data.edit()
        edit.putInt("specie", specie.id)
        edit.putInt("turn", 1)
        if(tutorial == 1) edit.putInt("tutorial", 2)
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


