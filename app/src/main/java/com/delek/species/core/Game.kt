package com.delek.species.core

import java.lang.reflect.Field

abstract class Game {

    //Borrar imagen al hacer click de nuevo en el adapter o al hacer onDrag o long click
    //Mostrar nombre, descripción y coste en el adapter
    //Asignar coste de los devices a la construcción de la nave, mostrar total
    //Mostrar power, strengh y speed de cada bloque
    //Comprobar que cada nave tiene un motor, un generador y un warp
    //Guardar todos los datos de la nave y almacenar en BD
    //Añadir Ship a la lista Prod
    //Volver al planeta y poner la nave en producción
    
    //TODO Comprobar que no se repita el astillero y borrar nave de tabla prod
    //TODO comprobar que no se repite el nombre?
    //TODO Ordenar el archivo strings.xml
    //TODO Mover los loads a cada helper correspondiente
    //TODO Incrementar Tutorial en dialogs, borrar OnPause

    //TODO Crear estilos para las vistas del device adapter
    //TODO Una sola vista para todas los device dhip

    //TODO Gestionar el aumento de población en los planetas
    //TODO Cambiar SECTOR1 a SECTOR2 al deslizar pantalla
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