package com.delek.species.ui

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.adapter.DevicesAdapter
import com.delek.species.dao.DeviceDAO
import com.delek.species.dao.SpecieDAO
import com.delek.species.databinding.FragmentShipyardBinding
import com.delek.species.model.Dialog
import com.delek.species.model.Game


class ShipyardFragment : Fragment() {

    private var _binding: FragmentShipyardBinding? = null
    private lateinit var adapter: DevicesAdapter
    private lateinit var v: ImageView
    private var days: Int = 0
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
        adapter = DevicesAdapter(devices, context)
        binding.shipDevicesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.shipDevicesRecyclerView.adapter = adapter

        viewListener()

        adapter.setOnItemClickListener {
            val resId = Game.getResId(it.image, R.drawable::class.java)
            if (discardType(v, it.type)) v.setImageResource(resId)
            binding.shipDevicesRecyclerView.visibility = View.GONE
        }

        return root
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
        return true
    }

    private fun discardType(v: ImageView, type: Int): Boolean {
        val dialog = Dialog(requireContext())
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


