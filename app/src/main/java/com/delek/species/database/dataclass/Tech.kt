package com.delek.species.database.dataclass

import java.io.Serializable

data class Tech(
    val id: Int = 0,
    val name: String = "",
    val cost: Int = 0,
    val require: Int = 0,
    val unlock: Int = 0
) : Serializable