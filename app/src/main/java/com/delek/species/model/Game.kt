package com.delek.species.model

import java.lang.reflect.Field

abstract class Game {

    //Borrar imagen al hacer click de nuevo en el adapter o al hacer onDrag o long click
    //Mostrar nombre, descripción y coste en el adapter
    //TODO Asignar coste de los devices a la construcción de la nave, mostrar total
    //TODO Mostrar power, strengh y speed de cada bloque
    //TODO Comprobar que cada nave tiene un motor, un generador y un warp
    //TODO Guardar todos los datos de la nave y almacenar en BD
    //TODO Gestionar el aumento de población en los planetas
    //TODO (Posterior) Crear un filtro segun el tipo de device en el adapter

    //TODO Estudiar corutinas Kotlin para IA

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