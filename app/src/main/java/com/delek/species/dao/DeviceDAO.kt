package com.delek.species.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Device
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.DeviceHelper


class DeviceDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) {  }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    private fun getColumns(cursor: Cursor): Device {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_NAME))
        val desc = cursor.getString(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_DESC))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_IMAGE))
        val type = cursor.getInt(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_TYPE))
        val cost = cursor.getInt(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_COST))
        val power = cursor.getInt(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_POWER))
        val techId = cursor.getInt(cursor.getColumnIndexOrThrow(DeviceHelper.COLUMN_TECH_ID))

        val device = Device(id, name, desc, image, type, cost, power, techId)
        return device
    }

    fun insertDevices(device: Device){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DeviceHelper.COLUMN_NAME, device.name)
            put(DeviceHelper.COLUMN_DESC, device.desc)
            put(DeviceHelper.COLUMN_IMAGE, device.image)
            put(DeviceHelper.COLUMN_TYPE, device.type)
            put(DeviceHelper.COLUMN_COST, device.cost)
            put(DeviceHelper.COLUMN_POWER, device.power)
            put(DeviceHelper.COLUMN_TECH_ID, device.techId)
        }
        db.insert(DeviceHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllDevices(): List<Device> {
        val db = readableDatabase
        val deviceList = mutableListOf<Device>()
        val query = "SELECT * FROM devices"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            deviceList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return deviceList
    }

    fun getDevicesByShip(shipId: Int?): List<Device> {
        val db = readableDatabase
        val deviceList = mutableListOf<Device>()
        val query = "SELECT devices.* FROM devices INNER JOIN ship_devices " +
                "ON devices.id = ship_devices.device_id " +
                "WHERE ship_devices.ship_id = $shipId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            deviceList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return deviceList
    }

    fun getColonyDevice(id: Int): Boolean {
        val db = readableDatabase
        val query = "SELECT devices.* FROM devices INNER JOIN ship_devices " +
                "ON devices.id = ship_devices.device_id " +
                "WHERE ship_devices.ship_id = $id AND ship_devices.device_id = 1"
        val cursor = db.rawQuery(query, null)
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun getDeviceById(deviceId: Int): Device {
        val db = readableDatabase
        var device = Device()
        val query = "SELECT * FROM devices WHERE id = $deviceId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            device = getColumns(cursor)
        }
        cursor.close()
        db.close()
        return device
    }

    fun getDevicesByTech(techId: Int): List<Device> {
        val db = readableDatabase
        val deviceList = mutableListOf<Device>()
        val query = "SELECT devices.* FROM devices INNER JOIN techs " +
                "ON devices.tech_id = techs.id WHERE techs.id = $techId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            deviceList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return deviceList
    }

    fun getDevicesByTechLearned(): List<Device> {
        val db = readableDatabase
        val deviceList = mutableListOf<Device>()
        val query = "SELECT devices.* FROM devices INNER JOIN tech_learned " +
                "ON devices.tech_id = tech_learned.tech_id " +
                "WHERE tech_learned.learned = 1"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            deviceList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return deviceList
    }

    fun getDevicesByType(typeId: Int): List<Device> {
        val db = readableDatabase
        val deviceList = mutableListOf<Device>()
        val query = "SELECT * FROM devices WHERE type = $typeId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            deviceList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return deviceList
    }


}