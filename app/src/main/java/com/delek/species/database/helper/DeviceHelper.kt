package com.delek.species.database.helper

class DeviceHelper {

    companion object{
        const val TABLE_NAME: String = "ships"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_DESC: String = "desc"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_COST: String = "cost"
        const val COLUMN_POWER: String = "power"
        const val COLUMN_TECH_ID: String = "tech_id"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_DESC TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_TYPE INTEGER,")
            append("$COLUMN_COST INTEGER,")
            append("$COLUMN_POWER INTEGER,")
            append("${COLUMN_TECH_ID}_ INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}