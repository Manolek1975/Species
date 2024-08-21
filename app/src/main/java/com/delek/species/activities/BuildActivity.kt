package com.delek.species.activities

import android.content.Context
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
import com.delek.species.Dialog as Dialog


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
        val data = this.getSharedPreferences("game_data", Context.MODE_PRIVATE)
        val tech = data.getInt("tech", 0)

        builds = BuildDAO(this)
        adapter = BuildsAdapter(builds.getBuildsByTech(tech), planet, this)
        binding.buildsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.buildsRecyclerView.adapter = adapter

    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(this)
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if(tutorial == 4){
            dialog.showTutorial(4)
        }
    }

    override fun onPause(){
        super.onPause()
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        val edit = data.edit()
        if(tutorial == 4) edit.putInt("tutorial", 5)
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