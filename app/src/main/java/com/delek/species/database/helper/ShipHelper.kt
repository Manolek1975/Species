package com.delek.species.database.helper

class ShipHelper {

    companion object{
        const val TABLE_NAME: String = "ships"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_SPECIE_ID: String = "specie_id"
        const val COLUMN_ORBIT: String = "orbit"
        const val COLUMN_ROUTE: String = "route"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_SPECIE_ID INTEGER,")
            append("$COLUMN_ORBIT INTEGER,")
            append("$COLUMN_ROUTE INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}