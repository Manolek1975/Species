package com.delek.species.database.dataclass

import java.io.Serializable

data class Tech(
    val id: Int = 0,
    val name: String = "",
    val image: String = "",
    val require: Int = 0,
    val unlock: Int = 0,
    val cost: Int = 0,
    val build: Int = 0,
    val device: Int = 0
) : Serializable