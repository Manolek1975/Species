package com.delek.species.database.helper

class StarExploredHelper {

    companion object{
        const val TABLE_NAME: String = "star_explored"
        const val COLUMN_ID: String = "id"
        const val COLUMN_SPECIE_ID: String = "specie_id"
        const val COLUMN_STAR_ID: String = "star_id"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_SPECIE_ID INTEGER,")
            append("$COLUMN_STAR_ID INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

}