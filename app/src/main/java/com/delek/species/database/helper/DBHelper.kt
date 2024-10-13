package com.delek.species.database.helper

import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME: String = "db_species"
        const val DATABASE_VERSION: Int = 3
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SpecieHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(StarHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(PlanetHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(BuildHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(TechHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(PlanetBuildsHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(ShipHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(DeviceHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(ShipDevicesHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(StarExploredHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(PlanetExploredHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(TechLearnedHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(ProdHelper.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SpecieHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(StarHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlanetHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(BuildHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(TechHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlanetBuildsHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(ShipHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(DeviceHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(ShipDevicesHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(StarExploredHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlanetExploredHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(TechLearnedHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(ProdHelper.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun onDelete() {
        val db = writableDatabase
        db?.execSQL(SpecieHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(StarHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlanetHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(BuildHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(TechHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlanetBuildsHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(ShipHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(DeviceHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(ShipDevicesHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(StarExploredHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlanetExploredHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(TechLearnedHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(ProdHelper.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun isEmpty(table: String?): Boolean {
        val database = this.readableDatabase
        val numRows = DatabaseUtils.queryNumEntries(database, table)

        return numRows == 0L
    }

}