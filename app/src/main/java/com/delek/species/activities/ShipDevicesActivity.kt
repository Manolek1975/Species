package com.delek.species.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.adapter.ShipDevicesAdapter
import com.delek.species.database.dao.DeviceDAO
import com.delek.species.database.dataclass.Ship
import com.delek.species.databinding.ActivityShipDevicesBinding

class ShipDevicesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityShipDevicesBinding
    private lateinit var adapter: ShipDevicesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShipDevicesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hideSystemBars()

        val ship = intent.getSerializableExtra("ship") as Ship?

        // Ship Info
        val shipInfo: TextView = findViewById(R.id.shipInfo)
        val shipID = resources.getIdentifier(ship?.image, "drawable", packageName)
        shipInfo.setCompoundDrawablesWithIntrinsicBounds(shipID, 0, 0, 0)
        shipInfo.text = ship?.name

        val devices = DeviceDAO(this).getDevicesByShip(ship?.id)
        adapter = ShipDevicesAdapter(devices, this)
        binding.shipDevicesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.shipDevicesRecyclerView.adapter = adapter
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