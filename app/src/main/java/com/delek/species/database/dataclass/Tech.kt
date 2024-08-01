package com.delek.species.database.dataclass

data class Tech(
    val id: Int,
    val name: String,
    val cost: Int,
    val require: Int,
    val unlock: Int
)