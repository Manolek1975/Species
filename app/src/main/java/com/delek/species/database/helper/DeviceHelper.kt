package com.delek.species.database.helper

import android.content.Context
import com.delek.species.R
import com.delek.species.dao.DeviceDAO
import com.delek.species.database.model.Device

class DeviceHelper {

    companion object{
        const val TABLE_NAME: String = "devices"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_DESC: String = "desc"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_COST: String = "cost"
        const val COLUMN_SPEED: String = "speed"
        const val COLUMN_POWER: String = "power"
        const val COLUMN_OFFENSE: String = "offense"
        const val COLUMN_DEFENSE: String = "defense"
        const val COLUMN_TECH_ID: String = "tech_id"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_DESC TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_TYPE INTEGER,")
            append("$COLUMN_COST INTEGER,")
            append("$COLUMN_SPEED INTEGER,")
            append("$COLUMN_POWER INTEGER,")
            append("$COLUMN_OFFENSE INTEGER,")
            append("$COLUMN_DEFENSE INTEGER,")
            append("$COLUMN_TECH_ID INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        fun loadDevices(context: Context) {
            val res = context.resources
            val name = res.getStringArray(R.array.name_devices)
            val desc = res.getStringArray(R.array.desc_devices)
            val image = res.getStringArray(R.array.image_devices)
            val type = res.getStringArray(R.array.type_devices)
            val cost = res.getStringArray(R.array.cost_devices)
            val speed = res.getStringArray(R.array.speed_devices)
            val power = res.getStringArray(R.array.power_devices)
            val offense = res.getStringArray(R.array.offense_devices)
            val defense = res.getStringArray(R.array.defense_devices)
            val tech = res.getStringArray(R.array.tech_devices)

            for (i in name.indices){
                val device = Device(0, name[i], desc[i], image[i], type[i].toInt(), cost[i].toInt(),
                    speed[i].toInt(), power[i].toInt(), offense[i].toInt(), defense[i].toInt(), tech[i].toInt())
                DeviceDAO(context).insertDevices(device)
            }
        }
    }



}