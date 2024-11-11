package com.delek.species.database.helper

class TechLearnedHelper {

    companion object{
        const val TABLE_NAME: String = "tech_learned"
        const val COLUMN_ID: String = "id"
        const val COLUMN_SPECIE_ID: String = "specie_id"
        const val COLUMN_TECH_ID: String = "tech_id"
        const val COLUMN_LEARNED: String = "learned"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_SPECIE_ID INTEGER,")
            append("$COLUMN_TECH_ID INTEGER,")
            append("$COLUMN_LEARNED INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}