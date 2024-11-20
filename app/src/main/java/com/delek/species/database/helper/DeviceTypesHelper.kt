package com.delek.species.database.helper

class DeviceTypesHelper {

    companion object{
        const val TABLE_NAME: String = "device_types"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"


        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}