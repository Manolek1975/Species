package com.delek.species.database.dataclass

import java.io.Serializable

data class Planet(
    val id: Int,
    val star: Int,
    val name: String,
    val image: String,
    val size: Int,
    val type: Int,
    val owner: Int,
    val food: Int,
    val production: Int,
    val population: Int,
    val research: Int,
    val explore: Int
) : Serializable


