package com.delek.species.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.adapter.DevicesAdapter
import com.delek.species.dao.DeviceDAO
import com.delek.species.dao.PlanetDAO
import com.delek.species.dao.ShipDAO
import com.delek.species.dao.ShipDevicesDAO
import com.delek.species.dao.SpecieDAO
import com.delek.species.database.model.Device
import com.delek.species.database.model.Planet
import com.delek.species.database.model.Ship
import com.delek.species.database.model.ShipDevices
import com.delek.species.databinding.FragmentShipyardBinding
import com.delek.species.model.Dialog
import com.delek.species.model.Game


class ShipyardFragment : Fragment() {

    private var _binding: FragmentShipyardBinding? = null
    private lateinit var adapter: DevicesAdapter
    private lateinit var deviceList: MutableMap<String, Device>
    private lateinit var planet: Planet
    private lateinit var dialog: Dialog
    private lateinit var v: ImageView
    private var totalDays = 0
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShipyardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specieId = data.getInt("specie", 0)
        val planetId = data.getInt("planet", 0)
        val specie = SpecieDAO(context).getSpecieById(specieId)

        dialog = Dialog(context)
        planet = PlanetDAO(context).getPlanetById(planetId)

        // Header
        val id = Game.getResId(specie.ship, R.drawable::class.java)
        binding.shipImage.setImageResource(id)
        binding.editNameShip.hint = specie.ship
        binding.editNameShip.setText(specie.ship)
        binding.editNameShip.setTextColor(Color.parseColor(specie.color))
        binding.daysLeft.text = getString(R.string.total_dias, 0)
        binding.editNameShip.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE)
                binding.editNameShip.clearFocus()
            false
        }
        binding.editButton.setOnClickListener {
            val list: MutableList<Int> = mutableListOf()
            deviceList.keys.forEach {
                val type = deviceList[it]!!.type
                list.add(type)
            }
            //Check if add engine, generator and warp
            if (list.contains(1) && list.contains(2) && list.contains(3)){
                //TODO Aquí no debería insertar nada, solo al terminar produccion en planeta
                //val res = context.resources
                //val image = res.getStringArray(R.array.image_ships)
                //var ship = Ship(0, binding.editNameShip.text.toString(), image[specieId-1], specieId, planet.id, 0)
                //ShipDAO(context).insertShips(ship)
                for(device in deviceList){
                    //TODO comprobar que no se repite el nombre?
                    val nave = "X101" //Nombre de la nave
                    //TODO Insertar como mutableSet en SharedPreferences
                    val test = mutableSetOf("1", "2", "3") //ID de cada device
                    data.edit().putStringSet(nave, test).apply()
                    //TODO Tampoco se insertan los devices aquí
                    //val shipId = ShipDAO(context).getLastShip()
                    //ship = ShipDAO(context).getShipById(shipId)
                    //val shipDevices = ShipDevices(0, shipId, device.value.id)
                    //ShipDevicesDAO(context).insertShipDevices(shipDevices)
                }
                //TODO Aún no sabemos el ID e la nave
                val res = context.resources
                val image = res.getStringArray(R.array.image_ships)
                val ship = Ship(0, binding.editNameShip.text.toString(), image[specieId-1], specieId, planet.id, 0)
                dialog.insertProdShipyard(ship, planet, totalDays)

            } else {
                dialog.showAlert("Introduce al menos un WARP, un generador y un motor")
            }
        }

        // Devices
        val devices = DeviceDAO(context).getDevicesByTechLearned()
        adapter = DevicesAdapter(devices, context)
        binding.shipDevicesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.shipDevicesRecyclerView.adapter = adapter

        viewListener()
        deviceList = mutableMapOf()

        adapter.setOnItemClickListener {
            val resId = Game.getResId(it.image, R.drawable::class.java)
            if (discardType(v, it.type)) {
                v.setImageResource(resId)
                deviceList[v.tag.toString()] = it
                addDays()
            }
            binding.shipDevicesRecyclerView.visibility = View.GONE
        }

        return root
    }

    private fun addDays()  {
        totalDays = 0
        var totalSpeed = 0
        var totalPower = 0
        var totalOffense = 0
        var totalDefense = 0
        for (device in deviceList) {
            totalDays += device.value.cost / planet.production
            totalSpeed += device.value.speed
            totalPower += device.value.power
            totalOffense += device.value.offense
            totalDefense += device.value.defense
        }
        binding.daysLeft.text = getString(R.string.total_dias, totalDays)
        binding.devicesLayout.speedTotalText.text = getString(R.string.total_speed, totalSpeed)
        binding.devicesLayout.powerTotalText.text = getString(R.string.total_power, totalPower)
        binding.devicesLayout.offenseTotalText.text = getString(R.string.total_offense, totalOffense)
        //binding.devicesLayout.defenseTotalText.text = getString(R.string.total_defense, totalDefense)
    }

    private fun viewListener() {
        val engineView1 = binding.devicesLayout.engineView1
        val engineView2 = binding.devicesLayout.engineView2
        val engineView3 = binding.devicesLayout.engineView3
        val engineView4 = binding.devicesLayout.engineView4
        val powerView1 = binding.devicesLayout.powerView1
        val powerView2 = binding.devicesLayout.powerView2
        val powerView3 = binding.devicesLayout.powerView3
        val powerView4 = binding.devicesLayout.powerView4
        val warpView1 = binding.devicesLayout.warpView1
        val warpView2 = binding.devicesLayout.warpView2
        val warpView3 = binding.devicesLayout.warpView3
        val warpView4 = binding.devicesLayout.warpView4

        val engineView = arrayListOf(
            engineView1, engineView2, engineView3, engineView4,
            powerView1, powerView2, powerView3, powerView4,
            warpView1, warpView2, warpView3, warpView4
        )

        engineView.forEach { v ->
            v.setOnClickListener {
                viewSelected(v)
            }
            v.setOnLongClickListener {
                viewDelete(v)
            }
        }
    }

    private fun viewSelected(view: ImageView): ImageView {
        binding.shipDevicesRecyclerView.visibility = View.VISIBLE
        v = view
        return v
    }

    private fun viewDelete(view: ImageView): Boolean {
        view.setImageResource(R.drawable.square_layout)
        deviceList.remove(view.tag)
        addDays()
        if (deviceList.isEmpty()) binding.shipDevicesRecyclerView.visibility = View.GONE
        return true
    }

    private fun discardType(v: ImageView, type: Int): Boolean {

        val range: Int = v.tag.toString().toInt()
        when (range) {
            in 1..4 -> if (type != 1) {
                dialog.showAlert("Sólo admite motores")
                return false
            }
            in 5..8 -> if (type != 2) {
                dialog.showAlert("Sólo admite generadores")
                return false
            }
            in 9..12 -> if (type != 3) {
                dialog.showAlert("Sólo admite WARP")
                return false
            }
        }
        return true
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


