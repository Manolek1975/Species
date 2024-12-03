package com.delek.species.ui.activities.specie

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.delek.species.database.dao.SpecieDAO
import com.delek.species.database.model.Specie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecieViewModel @Inject constructor(private val specieDao: SpecieDAO): ViewModel() {

    private val specieModel = MutableLiveData<Specie>()
    private val isLoading = MutableLiveData<Boolean>()

    fun onCreate(){
        viewModelScope.launch {
            isLoading.postValue(true)
            val result = specieDao.getAllSpecies()

            if (result.isNotEmpty()) {
                specieModel.postValue(result[0])
                isLoading.postValue(false)
            }
        }
    }

}