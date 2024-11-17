package com.delek.species.model

import java.lang.reflect.Field

abstract class Game {

    //TODO Continuar tutorial, asignar builds a techs
    //TODO Estudiar corutinas Kotlin

    companion object {
        fun getResId(resName: String?, c: Class<*>): Int {
            try {
                val idField: Field = c.getDeclaredField(resName!!)
                return idField.getInt(idField)
            } catch (e: Exception) {
                e.printStackTrace()
                return -1
            }
        }





    }

}