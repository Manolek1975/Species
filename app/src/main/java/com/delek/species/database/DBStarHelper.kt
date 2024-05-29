package com.delek.species.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBStarHelper(context: Context?) : SQLiteOpenHelper(context,
    DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        const val DATABASE_NAME: String = "db_species"
        const val DATABASE_VERSION: Int = 1
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

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableStars = buildString {
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
        db?.execSQL(createTableStars)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertStars(star: Star) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, star.id)
            put(COLUMN_NAME, star.name)
            put(COLUMN_IMAGE, star.image)
            put(COLUMN_SECTOR, star.sector)
            put(COLUMN_JUMPS, star.jumps)
            put(COLUMN_X, star.x)
            put(COLUMN_Y, star.y)
            put(COLUMN_TYPE, star.type)
            put(COLUMN_EXPLORE, star.explore)
        }
        db.insert(DBStarHelper.TABLE_NAME, null, values)
        db.close()
    }
}