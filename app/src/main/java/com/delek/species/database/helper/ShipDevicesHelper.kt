package com.delek.species.database.helper

class ShipDevicesHelper {

    companion object{
        const val TABLE_NAME: String = "ship_devices"
        const val COLUMN_ID: String = "id"
        const val COLUMN_SHIP_ID: String = "ship_id"
        const val COLUMN_DEVICE_ID: String = "device_id"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_SHIP_ID INTEGER,")
            append("$COLUMN_DEVICE_ID INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}