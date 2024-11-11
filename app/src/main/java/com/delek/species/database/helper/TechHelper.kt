package com.delek.species.database.helper

class TechHelper {
    companion object{
        const val TABLE_NAME: String = "techs"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_COST: String = "cost"
        const val COLUMN_REQUIRE: String = "require"
        const val COLUMN_UNLOCK: String = "unlock"
        const val COLUMN_BUILD: String = "build"
        const val COLUMN_DEVICE: String = "device"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_COST INTEGER,")
            append("$COLUMN_REQUIRE INTEGER,")
            append("$COLUMN_UNLOCK INTEGER,")
            append("$COLUMN_BUILD INTEGER,")
            append("$COLUMN_DEVICE INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}