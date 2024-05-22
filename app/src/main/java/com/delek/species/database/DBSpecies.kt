package com.delek.species.database

class DBSpecies {

    companion object {
        const val TABLE_NAME: String = "species"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_DESC: String = "description"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_SKILL: String = "skill"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_STAR: String = "star"

        val createTableSpecies = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY_KEY," +
                "$COLUMN_NAME TEXT," +
                "$COLUMN_DESC TEXT," +
                "$COLUMN_IMAGE TEXT," +
                "$COLUMN_SKILL TEXT," +
                "$COLUMN_TYPE INTEGER," +
                "$COLUMN_STAR TEXT)"

        val dropTableQuery = "DROP TABLE IF EXIST $TABLE_NAME"

    }

}