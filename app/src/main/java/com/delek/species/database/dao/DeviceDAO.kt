package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Device
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.PlanetBuilds
import com.delek.species.database.dataclass.Ship
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.DeviceHelper
import com.delek.species.database.helper.PlanetHelper
import com.delek.species.database.helper.PlanetBuildsHelper
import com.delek.species.database.helper.ShipHelper

class DeviceDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {

    fun getAllDevices(): List<Device> {
        var deviceList = mutableListOf<Device>()
        val db = readableDatabase
        val query = "SELECT * FROM devices"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_NAME))
            val desc = cursor.getString(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_DESC))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_IMAGE))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_TYPE))
            val cost = cursor.getInt(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_COST))
            val power = cursor.getInt(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_POWER))
            val techId = cursor.getInt(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_TECH_ID))

            val device = Device(id, name, desc, image, type, cost, power, techId)
            deviceList.add(device)
        }
        cursor.close()
        db.close()
        return deviceList
    }


    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }


}