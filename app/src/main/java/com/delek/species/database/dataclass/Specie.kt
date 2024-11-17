package com.delek.species.database.dataclass

import java.io.Serializable

data class Specie(
    val id : Int,
    val name : String,
    val desc : String,
    val image : String,
    val skill : String,
    val ship : String,
    val star : Int,
    val color : String,
    val origin : Int
) : Serializable

