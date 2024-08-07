package com.delek.species.database.dataclass

data class Device (
    val id: Int,
    val name: String,
    val desc: String,
    val image: String,
    val type: Int,
    val cost: Int,
    val power: Int,
    val techId: Int
){
}