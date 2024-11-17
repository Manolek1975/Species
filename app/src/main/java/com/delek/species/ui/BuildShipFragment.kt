package com.delek.species.ui

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.delek.species.R
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.databinding.FragmentBuildShipBinding
import com.delek.species.model.Dialog
import com.delek.species.model.Game


class BuildShipFragment : Fragment() {

    private var _binding: FragmentBuildShipBinding? = null
    //private lateinit var adapter: BuildShipAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBuildShipBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val speciesId = data.getInt("specie", 0)
        val specie = SpecieDAO(context).getSpecieById(speciesId)

        // Ship Info
        val id = Game.getResId(specie.ship, R.drawable::class.java)
        binding.shipImage.setImageResource(id)
        binding.shipName.hint = specie.ship
        binding.shipName.setText(specie.ship)

        binding.engineInfo.setOnClickListener {
            binding.engineInfo.setImageResource(R.drawable.d2)
        }

        binding.editButton.setOnClickListener {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().applicationWindowToken, 0)
            binding.shipName.clearFocus()
        }

/*        // Devices
        val devices = DeviceDAO(context).getDevicesByShip(ship.id)
        adapter = ShipDevicesAdapter(devices, ship.id, context)
        binding.shipDevicesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.shipDevicesRecyclerView.adapter = adapter*/

/*
        binding.imageShip.setOnClickListener {
            (activity as SidebarActivity).openDrawer()
        }
*/
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