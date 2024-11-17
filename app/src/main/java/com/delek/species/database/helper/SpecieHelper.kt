package com.delek.species.database.helper

class SpecieHelper {

    companion object{
        const val TABLE_NAME: String = "species"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_DESC: String = "description"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_SKILL: String = "skill"
        const val COLUMN_SHIP: String = "ship"
        const val COLUMN_STAR: String = "star"
        const val COLUMN_COLOR: String = "color"
        const val COLUMN_ORIGIN: String = "origin"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_DESC TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_SKILL TEXT,")
            append("$COLUMN_SHIP TEXT,")
            append("$COLUMN_STAR INTEGER,")
            append("$COLUMN_COLOR TEXT,")
            append("$COLUMN_ORIGIN INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}