package com.delek.species.database.model

import java.io.Serializable

data class Prod(
    val id: Int = 0,
    val type: Int = 0,
    val name: String = "",
    val typeId: Int = 0,
    val planet: Int = 0,
    val owner: Int = 0,
    val days: Int = 0
): Serializable