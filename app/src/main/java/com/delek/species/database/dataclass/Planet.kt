package com.delek.species.database.dataclass

import java.io.Serializable

data class Planet(
    val id: Int = 0,
    val star: Int = 0,
    val name: String = "",
    val image: String = "",
    val size: Int = 0,
    val type: Int = 0,
    val owner: Int = 0,
    val food: Int = 0,
    val production: Int = 0,
    val population: Int = 0,
    val research: Int = 0,
    val explore: Int = 0
) : Serializable


