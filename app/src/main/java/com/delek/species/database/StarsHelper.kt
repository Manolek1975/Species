package com.delek.species.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class StarsHelper(context: Context?) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {

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

        val createTable = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY_KEY")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_SECTOR TEXT,")
            append("$COLUMN_JUMPS INTEGER,")
            append("$COLUMN_X INTEGER,")
            append("$COLUMN_Y INTEGER,")
            append("$COLUMN_TYPE INTEGER,")
            append("$COLUMN_EXPLORE INTEGER)")
        }
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

}