package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.adapter.ShipDevicesAdapter
import com.delek.species.database.dao.DeviceDAO
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.databinding.FragmentShipDevicesBinding


class ShipDevicesFragment : Fragment() {

    private var _binding: FragmentShipDevicesBinding? = null
    private lateinit var adapter: ShipDevicesAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipDevicesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val shipId = data.getInt("ship", 0)
        val planetId = data.getInt("planet", 0)
        val ship = ShipDAO(context).getShipById(shipId)
        val planet = PlanetDAO(context).getPlanetById(planetId)

        // Ship Info
        val shipID = resources.getIdentifier(ship.image, "drawable", context.packageName)
        binding.shipInfo.setCompoundDrawablesWithIntrinsicBounds(shipID, 0, 0, 0)
        binding.shipInfo.text = ship.name

        // Devices
        val devices = DeviceDAO(context).getDevicesByShip(ship.id)
        adapter = ShipDevicesAdapter(devices, planet, ship.id, context)
        binding.shipDevicesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.shipDevicesRecyclerView.adapter = adapter
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}