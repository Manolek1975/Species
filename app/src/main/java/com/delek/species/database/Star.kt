package com.delek.species.database

import android.os.Parcelable
import java.io.Serializable

data class Star(
    val id: Int,
    val name: String,
    val image: String,
    val sector: String,
    val jumps: Int,
    val x: Int,
    val y: Int,
    val type: Int,
    val explore: Int
) : Serializable
