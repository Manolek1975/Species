package com.delek.species.database.dataclass

data class Ship (
    val id: Int,
    val name: String,
    val specie_id: Int,
    val orbit: Int,
    val route: Int,
    val days: Int
)
