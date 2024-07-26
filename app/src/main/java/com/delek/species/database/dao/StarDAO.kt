package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Specie
import com.delek.species.database.dataclass.Star
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.PlanetsHelper
import com.delek.species.database.helper.SpeciesHelper
import com.delek.species.database.helper.StarsHelper

class StarDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {

    fun insertStars(star: Star) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(StarsHelper.COLUMN_ID, star.id)
            put(StarsHelper.COLUMN_NAME, star.name)
            put(StarsHelper.COLUMN_IMAGE, star.image)
            put(StarsHelper.COLUMN_SECTOR, star.sector)
            put(StarsHelper.COLUMN_JUMPS, star.jumps)
            put(StarsHelper.COLUMN_X, star.x)
            put(StarsHelper.COLUMN_Y, star.y)
            put(StarsHelper.COLUMN_TYPE, star.type)
            put(StarsHelper.COLUMN_EXPLORE, star.explore)
        }
        db.insert(StarsHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllStars(): List<Star>{
        val starList = mutableListOf<Star>()
        val db = readableDatabase
        val query = "SELECT * FROM stars"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_IMAGE))
            val sector = cursor.getString(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_SECTOR))
            val jumps = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_JUMPS))
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_Y))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_TYPE))
            val explore = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_EXPLORE))

            val star = Star(id, name, image, sector, jumps, x, y, type, explore)
            starList.add(star)
        }
        cursor.close()
        db.close()
        return starList
    }

    fun isEmpty(table: String?): Boolean {
        val database = this.readableDatabase
        val numRows = DatabaseUtils.queryNumEntries(database, table)

        return numRows == 0L
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}