package com.delek.species.database.dataclass

data class Specie(
    val id : Int,
    val name : String,
    val desc : String,
    val image : String,
    val skill : String,
    val type : Int,
    val star : Int
)