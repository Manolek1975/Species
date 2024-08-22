package com.delek.species.database.helper

class StarHelper {
    companion object{
        const val TABLE_NAME: String = "stars"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_SECTOR: String = "sector"
        const val COLUMN_JUMPS: String = "jumps"
        const val COLUMN_X: String = "x"
        const val COLUMN_Y: String = "y"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_EXPLORE: String = "explore"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_SECTOR INTEGER,")
            append("$COLUMN_JUMPS INTEGER,")
            append("$COLUMN_X INTEGER,")
            append("$COLUMN_Y INTEGER,")
            append("$COLUMN_TYPE INTEGER,")
            append("$COLUMN_EXPLORE INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }


}