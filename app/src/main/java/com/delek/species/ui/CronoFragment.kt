package com.delek.species.ui

import android.content.Context
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delek.species.databinding.FragmentCronoBinding
import com.delek.species.model.Dialog


class CronoFragment: Fragment() {

    private var _binding: FragmentCronoBinding? = null
    //private lateinit var adapter: CronosAdapter
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCronoBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val turn = data.getInt("turn", 0)
        var fecha = 2300
        var days = turn

        val timer = object: CountDownTimer(50000, 100) {
            override fun onTick(millisUntilFinished: Long) {
                binding.fechaEstelar.text = buildString {
                    append(fecha)
                    append(".")
                    append(days)
                }
                days++
                if (days > 99) {
                    fecha++
                    days = 0
                }

            }
            override fun onFinish() {
                //binding.fechaEstelar.text = "OK"
            }
        }
        timer.start()

/*        adapter = BuildsAdapter(BuildDAO(context).getBuildsByTech(tech), planet, context)
        binding.buildsRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.buildsRecyclerView.adapter = adapter*/

        return root
    }

    override fun onResume(){
        super.onResume()
        val dialog = Dialog(requireContext())
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 8) dialog.showTutorial(8)
    }

    override fun onPause(){
        super.onPause()
        val data = context?.getSharedPreferences("data", Context.MODE_PRIVATE)
        val tutorial = data?.getInt("tutorial", 0)
        if(tutorial == 8) data.edit().putInt("tutorial", 9).apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}