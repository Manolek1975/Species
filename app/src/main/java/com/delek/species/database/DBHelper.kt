package com.delek.species.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    private lateinit var species: SpeciesHelper
    companion object {
        const val DATABASE_NAME: String = "db_species"
        const val DATABASE_VERSION: Int = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(species.createTable)
        db?.execSQL(StarsHelper.TABLE_NAME)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}