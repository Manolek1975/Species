package com.delek.species.database.helper

class OrbitalHelper {
    companion object{
        const val TABLE_NAME: String = "orbital"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_DESC: String = "description"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_TECH: String = "tech"
        const val COLUMN_COST: String = "cost"
        const val COLUMN_OFFENCE: String = "offence"
        const val COLUMN_DEFENSE: String = "defense"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_DESC TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_TECH INTEGER,")
            append("$COLUMN_COST INTEGER,")
            append("$COLUMN_OFFENCE INTEGER,")
            append("$COLUMN_DEFENSE INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}