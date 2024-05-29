package com.delek.species.database

import android.database.sqlite.SQLiteDatabase

class StarsHelper {

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
    }

    fun onCreate(db: SQLiteDatabase?) {
        val createTableStars = buildString {
            append("CREATE TABLE ${DBStarHelper.TABLE_NAME} (")
            append("${DBStarHelper.COLUMN_ID}} INTEGER PRIMARY_KEY")
            append("${DBStarHelper.COLUMN_NAME}} TEXT,")
            append("${DBStarHelper.COLUMN_IMAGE}} TEXT,")
            append("${DBStarHelper.COLUMN_SECTOR}} TEXT,")
            append("${DBStarHelper.COLUMN_JUMPS}} INTEGER,")
            append("${DBStarHelper.COLUMN_X}} INTEGER,")
            append("${DBStarHelper.COLUMN_Y}} INTEGER,")
            append("${DBStarHelper.COLUMN_TYPE}} INTEGER,")
            append("${DBStarHelper.COLUMN_EXPLORE}} INTEGER)")
        }
        db?.execSQL(createTableStars)
    }
}