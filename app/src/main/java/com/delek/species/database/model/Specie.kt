package com.delek.species.database.model

import java.io.Serializable

data class Specie(
    val id : Int,
    val name : String,
    val desc : String,
    val image : String,
    val ship : String,
    val imgShip : String,
    val skill : String,
    val star : Int,
    val color : String,
    val origin : Int
) : Serializable

