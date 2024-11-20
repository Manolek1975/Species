package com.delek.species.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.adapter.ShipDevicesAdapter
import com.delek.species.dao.DeviceDAO
import com.delek.species.dao.SpecieDAO
import com.delek.species.database.dataclass.Device
import com.delek.species.databinding.FragmentBuildShipBinding
import com.delek.species.model.Dialog
import com.delek.species.model.Game


class BuildShipFragment : Fragment() {

    private var _binding: FragmentBuildShipBinding? = null
    private lateinit var adapter: ShipDevicesAdapter
    private var device: Int = 0
    private var days: Int = 0
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuildShipBinding.inflate(inflater, container, false)

        val root: View = binding.root
        initListeners()

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val speciesId = data.getInt("specie", 0)
        val specie = SpecieDAO(context).getSpecieById(speciesId)

        // Header
        val id = Game.getResId(specie.ship, R.drawable::class.java)
        binding.shipImage.setImageResource(id)
        binding.shipName.hint = specie.ship
        binding.shipName.setText(specie.ship)
        binding.shipName.setTextColor(Color.parseColor(specie.color))
        binding.daysLeft.text = getString(R.string.dias, days)
        binding.editButton.setOnClickListener {
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().applicationWindowToken, 0)
            binding.shipName.clearFocus()
        }

        // Devices
        val devices = DeviceDAO(context).getDevicesByTechLearned()
        adapter = ShipDevicesAdapter(devices)
        binding.shipDevicesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.shipDevicesRecyclerView.adapter = adapter

        adapter.setOnItemClickListener {
            if (discardType(device, it.type))
                checkDevice(device, it)
            binding.shipDevicesRecyclerView.visibility = View.GONE
        }

        return root
    }

    private fun discardType(device: Int, type: Int): Boolean {
        val dialog = Dialog(requireContext())
        when (device) {
            in 1..4 -> if (type != 2) {
                dialog.showAlert("Sólo admite motores")
                return false
            }
            in 5..8 -> if (type != 3) {
                dialog.showAlert("Sólo admite generadores")
                return false
            }
            in 9..12 -> if (type != 5) {
                dialog.showAlert("Sólo admite WARP")
                return false
            }
        }
        return true
    }

    private fun checkDevice(device: Int, it: Device) {
        val resId = Game.getResId(it.image, R.drawable::class.java)
        when (device) {
            1 -> binding.devicesLayout.engineView1.setImageResource(resId)
            2 -> binding.devicesLayout.engineView2.setImageResource(resId)
            3 -> binding.devicesLayout.engineView3.setImageResource(resId)
            4 -> binding.devicesLayout.engineView4.setImageResource(resId)
            5 -> binding.devicesLayout.energyView1.setImageResource(resId)
            6 -> binding.devicesLayout.energyView2.setImageResource(resId)
            7 -> binding.devicesLayout.energyView3.setImageResource(resId)
            8 -> binding.devicesLayout.energyView4.setImageResource(resId)
            9 -> binding.devicesLayout.warpView1.setImageResource(resId)
            10 -> binding.devicesLayout.warpView2.setImageResource(resId)
            11 -> binding.devicesLayout.warpView3.setImageResource(resId)
            12 -> binding.devicesLayout.warpView4.setImageResource(resId)
        }
    }

    private fun initListeners() {
        binding.devicesLayout.engineView1.setOnClickListener {
            device = 1
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }
        binding.devicesLayout.engineView2.setOnClickListener {
            device = 2
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }
        binding.devicesLayout.engineView3.setOnClickListener {
            device = 3
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }
        binding.devicesLayout.engineView4.setOnClickListener {
            device = 4
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }
        binding.devicesLayout.energyView1.setOnClickListener {
            device = 5
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }
        binding.devicesLayout.energyView2.setOnClickListener {
            device = 6
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }
        binding.devicesLayout.energyView3.setOnClickListener {
            device = 7
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }
        binding.devicesLayout.energyView4.setOnClickListener {
            device = 8
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }
        binding.devicesLayout.warpView1.setOnClickListener {
            device = 9
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }
        binding.devicesLayout.warpView2.setOnClickListener {
            device = 10
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }
        binding.devicesLayout.warpView3.setOnClickListener {
            device = 11
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }
        binding.devicesLayout.warpView4.setOnClickListener {
            device = 12
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        }



        binding.devicesLayout.engineView1.setOnLongClickListener {
            binding.devicesLayout.engineView1.setImageResource(R.drawable.square_layout)
            true
        }

        binding.devicesLayout.engineView2.setOnLongClickListener {
            binding.devicesLayout.engineView2.setImageResource(R.drawable.square_layout)
            true
        }

        binding.devicesLayout.engineView3.setOnLongClickListener {
            binding.devicesLayout.engineView3.setImageResource(R.drawable.square_layout)
            true
        }

        binding.devicesLayout.engineView4.setOnLongClickListener {
            binding.devicesLayout.engineView4.setImageResource(R.drawable.square_layout)
            true
        }

    }

    private fun showAdapter(engine: Int, context: Context): List<Device> {
        return when (engine) {
            1 -> DeviceDAO(context).getDevicesByType(2)
            2 -> DeviceDAO(context).getDevicesByType(3)
            3 -> DeviceDAO(context).getDevicesByType(4)
            4 -> DeviceDAO(context).getDevicesByType(5)
            else -> DeviceDAO(context).getDevicesByType(0)
        }
    }

    override fun onResume() {
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if (tutorial == 7) dialog.showTutorial(7)
        if (tutorial == 17) dialog.showTutorial(17)
    }

    override fun onPause() {
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if (tutorial == 7) data.edit().putInt("tutorial", 8).apply()
        if (tutorial == 17) data.edit().putInt("tutorial", 18).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


