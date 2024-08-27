package com.delek.species.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.adapter.NavigationAdapter
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.databinding.ActivityNavigationBinding

class NavigationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNavigationBinding
    private lateinit var adapter: NavigationAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        binding = ActivityNavigationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val shipId = intent.getIntExtra("shipId", 0)
        val data = this.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        val ship = ShipDAO(this).getShipById(shipId)
        val star = StarDAO(this).getStarById(data.getInt("star", 0))
        val planet = PlanetDAO(this).getPlanetsByStarId(star.id)
        adapter = NavigationAdapter(planet, ship,this)
        binding.navigationRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.navigationRecyclerView.adapter = adapter

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