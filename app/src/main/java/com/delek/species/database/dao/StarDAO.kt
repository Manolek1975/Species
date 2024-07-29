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

    fun getStarNameBySpecie(specieId: Int): String{
        val starList = mutableListOf<Star>()
        var name = ""
        val db = readableDatabase
        val query = "SELECT * FROM stars WHERE id = $specieId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_NAME))
        }
        cursor.close()
        db.close()
        return name
    }


    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}