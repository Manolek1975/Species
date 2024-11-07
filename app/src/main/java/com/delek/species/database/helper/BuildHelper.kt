package com.delek.species.database.helper

class BuildHelper {
    companion object{
        const val TABLE_NAME: String = "builds"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_DESC: String = "description"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_TECH: String = "tech"
        const val COLUMN_COST: String = "cost"
        const val COLUMN_FOOD: String = "food"
        const val COLUMN_INDUSTRY: String = "industry"
        const val COLUMN_SCIENCE: String = "science"
        const val COLUMN_POPULATION: String = "population"
        const val COLUMN_OFFENCE: String = "offence"
        const val COLUMN_DEFENSE: String = "defense"
        const val COLUMN_INVADER: String = "invader"
        const val COLUMN_ORBITAL: String = "orbital"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_DESC TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_TECH INTEGER,")
            append("$COLUMN_COST INTEGER,")
            append("$COLUMN_FOOD INTEGER,")
            append("$COLUMN_INDUSTRY INTEGER,")
            append("$COLUMN_SCIENCE INTEGER,")
            append("$COLUMN_POPULATION INTEGER,")
            append("$COLUMN_OFFENCE INTEGER,")
            append("$COLUMN_DEFENSE INTEGER,")
            append("$COLUMN_INVADER INTEGER,")
            append("$COLUMN_ORBITAL INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}