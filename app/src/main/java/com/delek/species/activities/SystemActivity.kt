package com.delek.species.activities

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.Dialog
import com.delek.species.R
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.adapter.PlanetsAdapter
import com.delek.species.database.dataclass.Star
import com.delek.species.databinding.ActivitySystemBinding


class SystemActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySystemBinding
    private lateinit var adapter: PlanetsAdapter
    private lateinit var db: PlanetDAO


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        val star = intent.getSerializableExtra("star") as Star?

        db = PlanetDAO(this)
        adapter = PlanetsAdapter(db.getPlanetsByStarId(star?.id), this)
        binding.systemRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.systemRecyclerView.adapter = adapter

        val starInfo: TextView = findViewById(R.id.starInfo)
        val drawableId = resources.getIdentifier(star?.image, "drawable", packageName)
        starInfo.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
        starInfo.text = "\n" + star?.name

    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(this)
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if(tutorial == 2){
                dialog.showTutorial(2)
        }
    }

    override fun onPause(){
        super.onPause()
        val file = "game_data"
        val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        val edit = data.edit()
        if(tutorial == 2) edit.putInt("tutorial", 3)
        edit.apply()
        db.close()
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