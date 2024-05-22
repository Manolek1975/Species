package com.delek.species.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // If you change the database schema, you must increment the database version.
    companion object{
        const val DATABASE_NAME: String = "db_species"
        const val DATABASE_VERSION: Int = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DBSpecies.createTableSpecies)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(DBSpecies.dropTableQuery)
        onCreate(db)
    }

    fun insertSpecies(specie: Specie) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DBSpecies.COLUMN_ID, specie.id)
            put(DBSpecies.COLUMN_NAME, specie.name)
            put(DBSpecies.COLUMN_DESC, specie.desc)
            put(DBSpecies.COLUMN_IMAGE, specie.image)
            put(DBSpecies.COLUMN_SKILL, specie.skill)
            put(DBSpecies.COLUMN_TYPE, specie.type)
            put(DBSpecies.COLUMN_STAR, specie.star)
        }
        db.insert(DBSpecies.TABLE_NAME, null, values)
        db.close()
    }


}