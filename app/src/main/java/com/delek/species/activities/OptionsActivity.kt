package com.delek.species.activities

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.game.Dialog
import com.delek.species.database.helper.DBHelper
import com.delek.species.databinding.ActivityOptionsBinding


@Suppress("UNUSED_EXPRESSION")
class OptionsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOptionsBinding
    private lateinit var db: DBHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideSystemBars()
        binding = ActivityOptionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBHelper(this)
        val data = this.getSharedPreferences("data", MODE_PRIVATE)
        val tutorial = data.getInt("tutorial", 0)
        if (tutorial == 1) {
            binding.tutorialSwitch.isChecked = true
        }

        binding.tutorialSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                data.edit().putInt("tutorial", 1).apply()
            } else {
                data.edit().putInt("tutorial", 0).apply()
            }
        }

        binding.restartButton.setOnClickListener {
            val dialog = Dialog(this)
            dialog.showRestartDialog()
        }

        binding.backButton.setOnClickListener {
            finish()
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

