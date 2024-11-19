package com.delek.species.activities

import android.os.Bundle
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.adapter.SpeciesAdapter
import com.delek.species.dao.SpecieDAO
import com.delek.species.databinding.ActivitySpecieBinding

class SpecieActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpecieBinding
    private lateinit var adapter: SpeciesAdapter
    private lateinit var species: SpecieDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpecieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        species = SpecieDAO(this)
        adapter = SpeciesAdapter(species.getAllSpecies(), this)
        binding.speciesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.speciesRecyclerView.adapter = adapter

        onBackPressedDispatcher.addCallback(this) {
            finishAffinity()
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