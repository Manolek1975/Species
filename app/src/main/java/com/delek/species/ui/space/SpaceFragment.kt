package com.delek.species.ui.space

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.delek.species.R

class SpaceFragment : Fragment() {

    companion object {
        fun newInstance() = SpaceFragment()
    }

    private val viewModel: SpaceViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val context = requireContext()
        val drawSpace = DrawSpace(context)
        return drawSpace
        //return inflater.inflate(R.layout.fragment_space, container, false)
    }
}