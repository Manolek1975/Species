package com.delek.species.activities

import android.content.Context
import android.os.Bundle
import android.view.View.GONE
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.game.Dialog
import com.delek.species.R
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.adapter.PlanetsAdapter
import com.delek.species.database.dao.StarDAO
import com.delek.species.databinding.ActivitySystemBinding


class SystemActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySystemBinding
    private lateinit var adapter: PlanetsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        val data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val star = StarDAO(this).getStarById(data.getInt("star", 0))
        val starInfo: TextView = findViewById(R.id.starInfo)
        val drawableId = resources.getIdentifier(star.image, "drawable", packageName)
        starInfo.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
        starInfo.text = star.name

        if (star.explore != 0) {
            adapter = PlanetsAdapter(PlanetDAO(this).getPlanetsByStarId(star.id), this)
            binding.systemRecyclerView.layoutManager = LinearLayoutManager(this)
            binding.systemRecyclerView.adapter = adapter
            binding.explored.visibility = GONE
        }
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(this)
        val data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if(tutorial == 2){
                dialog.showTutorial(2)
        }
    }

    override fun onPause(){
        super.onPause()
        val data = this.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if(tutorial == 2)
            data.edit().putInt("tutorial", 3).apply()
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