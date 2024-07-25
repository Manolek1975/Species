package com.delek.species.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.database.DBHelper
import com.delek.species.database.SpeciesAdapter
import com.delek.species.databinding.ActivitySpecieBinding

class SpecieActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpecieBinding
    private lateinit var adapter: SpeciesAdapter
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        db = DBHelper(this)
        adapter = SpeciesAdapter(db.getAllSpecies(), this)
        binding.speciesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.speciesRecyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        //adapter.refreshData(db.getAllSpecies())
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