package com.delek.species

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.database.DBHelper
import com.delek.species.database.SpeciesAdapter
import com.delek.species.database.SpeciesHelper
import com.delek.species.databinding.ActivityInitialBinding

class InitialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitialBinding
    private lateinit var db: DBHelper
    private lateinit var adapter: SpeciesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

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