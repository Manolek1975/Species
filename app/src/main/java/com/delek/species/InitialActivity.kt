package com.delek.species

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.database.DBSpeciesHelper
import com.delek.species.databinding.ActivityInitialBinding



class InitialActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInitialBinding
    private lateinit var db: DBSpeciesHelper
    private lateinit var speciesAdapter: SpeciesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInitialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        db = DBSpeciesHelper(this)
        speciesAdapter = SpeciesAdapter(db.getAllSpecies(), this)

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