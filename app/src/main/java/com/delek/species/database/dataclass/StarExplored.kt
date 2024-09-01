package com.delek.species.database.dataclass

import java.io.Serializable

data class StarExplored(
    val id: Int,
    val specieId: Int,
    val starId: Int
) : Serializable
