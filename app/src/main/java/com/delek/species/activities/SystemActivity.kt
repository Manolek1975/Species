package com.delek.species.activities

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.core.view.setPadding
import com.delek.species.R
import com.delek.species.database.DBHelper
import com.delek.species.database.Star
import com.delek.species.databinding.ActivitySystemBinding



class SystemActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySystemBinding
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySystemBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        val star = intent.getSerializableExtra("star") as Star?

        val starInfo: TextView = findViewById(R.id.starInfo)
        val drawableId = resources.getIdentifier(star?.image, "drawable", packageName)
        starInfo.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0) // Left drawable
        starInfo.text = star?.name // Set the star name





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