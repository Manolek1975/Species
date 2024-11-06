package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.adapter.ShipDevicesAdapter
import com.delek.species.database.dao.DeviceDAO
import com.delek.species.database.dao.ShipDAO
import com.delek.species.databinding.FragmentShipDevicesBinding
import com.delek.species.model.Dialog
import com.delek.species.model.Game


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
        val ship = ShipDAO(context).getShipById(shipId)

        // Ship Info
        val id = Game.getResId(ship.image, R.drawable::class.java)
        binding.shipInfo.setCompoundDrawablesWithIntrinsicBounds(id, 0, 0, 0)
        binding.shipInfo.text = ship.name

        // Devices
        val devices = DeviceDAO(context).getDevicesByShip(ship.id)
        adapter = ShipDevicesAdapter(devices, ship.id, context)
        binding.shipDevicesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.shipDevicesRecyclerView.adapter = adapter
        return root
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 7) dialog.showTutorial(7)
        if(tutorial == 17) dialog.showTutorial(17)
    }

    override fun onPause(){
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 7) data.edit().putInt("tutorial", 8).apply()
        if(tutorial == 17) data.edit().putInt("tutorial", 18).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}