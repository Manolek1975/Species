package com.delek.species.database.model

data class Device (
    val id: Int = 0,
    val name: String = "",
    val desc: String = "",
    val image: String = "",
    val type: Int = 0,
    val cost: Int = 0,
    val speed: Int = 0,
    val power: Int = 0,
    val offense: Int,
    val defense: Int,
    val techId: Int = 0
)