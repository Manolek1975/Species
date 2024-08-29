package com.delek.species.activities


import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.game.Dialog
import com.delek.species.game.DrawStars
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.databinding.ActivitySectorBinding


class SectorActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySectorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySectorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        val data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specie = SpecieDAO(this).getSpecieById(data.getInt("specie", 0))
        val origin = StarDAO(this).getStarById(specie.origin)
        StarDAO(this).setStarExplored(origin.id) // Set origin star Explored
        data.edit().putInt("sector", origin.sector).apply()

        val drawStars = DrawStars(this)
        setContentView(drawStars)

    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(this)
        val data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if(tutorial == 1){
            dialog.showTutorial(1)
        }
    }

    override fun onPause(){
        super.onPause()
        val data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if(tutorial == 1)
            data.edit().putInt("tutorial", 2).apply()
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


