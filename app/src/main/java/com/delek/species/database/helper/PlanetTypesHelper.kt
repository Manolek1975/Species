package com.delek.species.database.helper

class PlanetTypesHelper {

    companion object{
        const val TABLE_NAME: String = "planet_types"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_FOOD: String = "food"
        const val COLUMN_PROD: String = "prod"
        const val COLUMN_TECH: String = "tech"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_FOOD INTEGER,")
            append("$COLUMN_PROD INTEGER,")
            append("$COLUMN_TECH INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}