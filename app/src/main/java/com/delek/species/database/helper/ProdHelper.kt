package com.delek.species.database.helper

class ProdHelper {
    companion object {
        const val TABLE_NAME: String = "prod"
        const val COLUMN_ID: String = "id"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_TYPE_ID: String = "type_id"
        const val COLUMN_PLANET: String = "planet"
        const val COLUMN_OWNER: String = "owner"
        const val COLUMN_DAYS: String = "days"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_TYPE INTEGER,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_TYPE_ID INTEGER,")
            append("$COLUMN_PLANET INTEGER,")
            append("$COLUMN_OWNER INTEGER,")
            append("$COLUMN_DAYS INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}