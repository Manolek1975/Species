package com.delek.species.ui.sector

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SectorViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Sector Fragment"
    }
    val text: LiveData<String> = _text
}