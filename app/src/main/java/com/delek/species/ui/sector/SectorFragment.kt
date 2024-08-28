package com.delek.species.ui.sector

import android.R
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.databinding.FragmentSectorBinding
import com.delek.species.game.DrawStars


class SectorFragment : Fragment() {

    private var _binding: FragmentSectorBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSectorBinding.inflate(inflater, container, false)

        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val specie = SpecieDAO(context).getSpecieById(data.getInt("specie", 0))
        val origin = StarDAO(context).getStarById(specie.origin)
        StarDAO(context).setStarExplored(origin.id) // Set origin star Explored
        data.edit().putInt("sector", origin.sector).apply()

        val drawStars = DrawStars(context)
        return drawStars

        //setContentView(drawStars)

        // Exit to MainActivity
/*        val i = Intent(context, MainActivity::class.java)
        var backTime = 0L
        onBackPressedDispatcher.addCallback(this) {
            if (backTime + 2000 > System.currentTimeMillis()) {
                startActivity(i)
            } else {
                Toast.makeText(this@SectorActivity, "Pulsa de nuevo para salir", Toast.LENGTH_SHORT).show()
            }
            backTime = System.currentTimeMillis()
        }*/

        //val root: View = binding.root
        //return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}