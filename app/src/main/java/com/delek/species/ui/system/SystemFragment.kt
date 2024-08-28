package com.delek.species.ui.system

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.delek.species.R
import com.delek.species.adapter.PlanetsAdapter
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.databinding.FragmentSystemBinding

class SystemFragment : Fragment() {

    private var _binding: FragmentSystemBinding? = null
    private lateinit var adapter: PlanetsAdapter

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SystemViewModel::class.java)

        _binding = FragmentSystemBinding.inflate(inflater, container, false)
        val root: View = binding.root

/*        val textView: TextView = binding.textSystem
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/
        val context = requireContext()
        val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
        val star = StarDAO(context).getStarById(data.getInt("star", 0))

        //val starInfo: TextView = findViewById(R.id.starInfo)
        val drawableId = resources.getIdentifier(star.image, "drawable", context.packageName )
        binding.starInfo.setCompoundDrawablesWithIntrinsicBounds(drawableId, 0, 0, 0)
        binding.starInfo.text = star.name

        if (star.explore != 0) {
            adapter = PlanetsAdapter(PlanetDAO(context).getPlanetsByStarId(star.id), context)
            binding.systemRecyclerView.layoutManager = LinearLayoutManager(context)
            binding.systemRecyclerView.adapter = adapter
            binding.explored.visibility = View.GONE
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}