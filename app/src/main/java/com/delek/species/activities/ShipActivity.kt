package com.delek.species.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.adapter.ShipsAdapter
import com.delek.species.adapter.SpeciesAdapter
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.databinding.ActivityShipBinding

class ShipActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShipBinding
    private lateinit var adapter: ShipsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        binding = ActivityShipBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val data = this.getSharedPreferences("game_data", MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val ship = ShipDAO(this).getShipsBySpecie(specieId)
        adapter = ShipsAdapter(ship, this)
        binding.shipsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.shipsRecyclerView.adapter = adapter

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