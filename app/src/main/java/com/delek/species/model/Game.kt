package com.delek.species.model

import android.content.Context
import com.delek.species.database.dataclass.Planet

abstract class Game(context: Context) {

    val context = context
    private fun advanceTurn(planet: Planet){
        Dialog(context).buildFinish(planet)
    }
}