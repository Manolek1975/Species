package com.delek.species.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.delek.species.Dialog
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

        binding.tutorialSwitch.setOnCheckedChangeListener { _, isChecked ->
            val file = "game_data"
            val data = this.getSharedPreferences(file, Context.MODE_PRIVATE)
            val edit = data.edit()
            if (isChecked) {
                edit.putInt("tutorial", 1)
            } else {
                edit.putInt("tutorial", 0)
            }
            edit.apply()
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

