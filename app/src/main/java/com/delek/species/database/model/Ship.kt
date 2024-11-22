package com.delek.species.database.model

import java.io.Serializable

data class Ship (
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val specieId: Int = 0,
    val orbit: Int = 0,
    val route: Int = 0,
) : Serializable
