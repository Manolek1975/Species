package com.delek.species.database.helper

class PlanetHelper {
    companion object{
        const val TABLE_NAME: String = "planets"
        const val COLUMN_ID: String = "id"
        const val COLUMN_STAR: String = "star"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_SIZE: String = "size"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_OWNER: String = "owner"
        const val COLUMN_FOOD: String = "food"
        const val COLUMN_PRODUCTION: String = "production"
        const val COLUMN_POPULATION: String = "population"
        const val COLUMN_RESEARCH: String = "research"
        const val COLUMN_EXPLORE: String = "explore"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_STAR INTEGER,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_SIZE TEXT,")
            append("$COLUMN_TYPE INTEGER,")
            append("$COLUMN_OWNER INTEGER,")
            append("$COLUMN_FOOD INTEGER,")
            append("$COLUMN_PRODUCTION INTEGER,")
            append("$COLUMN_POPULATION INTEGER,")
            append("$COLUMN_RESEARCH INTEGER,")
            append("$COLUMN_EXPLORE INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}