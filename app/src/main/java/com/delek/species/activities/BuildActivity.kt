package com.delek.species.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.database.adapter.BuildsAdapter
import com.delek.species.database.dao.BuildDAO
import com.delek.species.database.dataclass.Planet
import com.delek.species.databinding.ActivityBuildBinding


class BuildActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBuildBinding
    private lateinit var adapter: BuildsAdapter
    private lateinit var builds: BuildDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuildBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        val planet = intent.getSerializableExtra("planet") as Planet

        builds = BuildDAO(this)
        adapter = BuildsAdapter(builds.getAllBuilds(), planet, this)
        binding.buildsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.buildsRecyclerView.adapter = adapter

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