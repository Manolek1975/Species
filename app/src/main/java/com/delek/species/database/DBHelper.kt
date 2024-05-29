package com.delek.species.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME: String = "db_species"
        const val DATABASE_VERSION: Int = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SpeciesHelper.SQL_CREATE_ENTRIES)
        //db?.execSQL(StarsHelper.createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SpeciesHelper.SQL_DELETE_ENTRIES);
        onCreate(db)
    }
}