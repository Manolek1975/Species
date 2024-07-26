package com.delek.species.database.dataclass

import java.io.Serializable

data class Build (
    val id: Int,
    val name: String,
    val description: String,
    val image: String,
    val tech: Int,
    val cost: Int,
    val food: Int,
    val industry: Int,
    val science: Int,
    val population: Int,
    val offense: Int,
    val defense: Int,
    val invader: Int
) : Serializable
