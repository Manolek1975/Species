package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.adapter.ShipDevicesAdapter
import com.delek.species.database.dao.DeviceDAO
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.databinding.FragmentBuildShipBinding
import com.delek.species.model.Dialog
import com.delek.species.model.Game


class BuildShipFragment : Fragment() {

    private var _binding: FragmentBuildShipBinding? = null
    //private lateinit var engineItem: EngineItemBinding  //merge_layout.xml layout

    private lateinit var adapter: ShipDevicesAdapter
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

        // Header
        val id = Game.getResId(specie.ship, R.drawable::class.java)
        binding.shipImage.setImageResource(id)
        binding.shipName.hint = specie.ship
        binding.shipName.setText(specie.ship)

        binding.editButton.setOnClickListener {
            val imm = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().applicationWindowToken, 0)
            binding.shipName.clearFocus()
        }

        // Devices
        val devices = DeviceDAO(context).getDevicesByType(2)
        adapter = ShipDevicesAdapter(devices)
        binding.shipDevicesRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.shipDevicesRecyclerView.adapter = adapter

        var engine = 0
        binding.engineLayout.engineView1.setOnClickListener {
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
            engine = 1
        }

        binding.engineLayout.engineView2.setOnClickListener {
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
            engine = 2
        }

        binding.engineLayout.engineView3.setOnClickListener {
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
            engine = 3
        }

        binding.engineLayout.engineView4.setOnClickListener {
            binding.shipDevicesRecyclerView.visibility = View.VISIBLE
            engine = 4
        }

        adapter.setOnItemClickListener {
            val resId = Game.getResId(it.image, R.drawable::class.java)
            when(engine){
                1 -> binding.engineLayout.engineView1.setImageResource(resId)
                2 -> binding.engineLayout.engineView2.setImageResource(resId)
                3 -> binding.engineLayout.engineView3.setImageResource(resId)
                4 -> binding.engineLayout.engineView4.setImageResource(resId)
            }
            binding.shipDevicesRecyclerView.visibility = View.GONE
        }


/*        binding.imageShip.setOnClickListener {
            (activity as SidebarActivity).openDrawer()
        }*/

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