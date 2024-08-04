package com.delek.species.database.helper

class PlanetBuildsHelper {

    companion object{
        const val TABLE_NAME: String = "planet_builds"
        const val COLUMN_ID: String = "id"
        const val COLUMN_PLANET_ID: String = "planet_id"
        const val COLUMN_BUILD_ID: String = "build_id"
        const val COLUMN_NIVEL: String = "nivel"
        const val COLUMN_DAYSLEFT: String = "days_left"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_PLANET_ID TEXT,")
            append("$COLUMN_BUILD_ID TEXT,")
            append("$COLUMN_NIVEL TEXT,")
            append("$COLUMN_DAYSLEFT INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}