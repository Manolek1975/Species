package com.delek.species

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.delek.species.database.DBSpeciesHelper
import com.delek.species.databinding.ActivityDialogBinding

class DialogActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDialogBinding
    private lateinit var db: DBSpeciesHelper
    private var specieId: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDialogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = DBSpeciesHelper(this)
        specieId = intent.getIntExtra("specie_id", -1)
        if (specieId == -1){
            finish()
            return
        }

        val specie = db.getSpecieById(specieId)
        binding.testSpecie.setText(specie.name)
    }
}