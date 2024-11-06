package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Orbital
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.OrbitalHelper


class OrbitalDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }


    fun insertOrbital(orbital: Orbital){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(OrbitalHelper.COLUMN_NAME, orbital.name)
            put(OrbitalHelper.COLUMN_DESC, orbital.description)
            put(OrbitalHelper.COLUMN_IMAGE, orbital.image)
            put(OrbitalHelper.COLUMN_TECH, orbital.tech)
            put(OrbitalHelper.COLUMN_COST, orbital.cost)
            put(OrbitalHelper.COLUMN_OFFENCE, orbital.offense)
            put(OrbitalHelper.COLUMN_DEFENSE, orbital.defense)
        }
        db.insert(OrbitalHelper.TABLE_NAME, null, values)
        db.close()
    }


}