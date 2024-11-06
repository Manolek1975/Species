package com.delek.species.database.dataclass

import java.io.Serializable

data class Orbital (
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val image: String = "",
    val tech: Int = 0,
    val cost: Int = 0,
    val offense: Int = 0,
    val defense: Int = 0,
) : Serializable
