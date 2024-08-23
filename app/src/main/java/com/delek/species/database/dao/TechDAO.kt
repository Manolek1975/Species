package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.PlanetBuilds
import com.delek.species.database.dataclass.Tech
import com.delek.species.database.helper.BuildHelper
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.TechHelper

class TechDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {

    fun insertTechs(tech: Tech){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TechHelper.COLUMN_NAME, tech.name)
            put(TechHelper.COLUMN_COST, tech.cost)
            put(TechHelper.COLUMN_REQUIRE, tech.require)
            put(TechHelper.COLUMN_UNLOCK, tech.unlock)
        }
        db.insert(TechHelper.TABLE_NAME, null, values)
        db.close()
    }


    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}