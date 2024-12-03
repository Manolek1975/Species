package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.model.ShipDevices
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.ShipDevicesHelper


class ShipDevicesDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun insertShipDevices(shipDevice: ShipDevices) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(ShipDevicesHelper.COLUMN_SHIP_ID, shipDevice.shipId)
            put(ShipDevicesHelper.COLUMN_DEVICE_ID, shipDevice.deviceId)
        }
        db.insert(ShipDevicesHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun removeColonyDevice(shipId: Int, deviceId: Int) {
        val db = writableDatabase
        val query = "DELETE FROM ship_devices WHERE ship_id = $shipId AND  device_id = $deviceId"
        db.execSQL(query)
        db.close()
    }


}