package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.model.Specie
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.SpecieHelper

class SpecieDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    private fun getColumns(cursor: Cursor): Specie {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_NAME))
        val desc = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_DESC))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_IMAGE))
        val skill = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_SKILL))
        val ship = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_SHIP))
        val imgShip = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_IMG_SHIP))
        val star = cursor.getInt(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_STAR))
        val color = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_COLOR))
        val origin = cursor.getInt(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_ORIGIN))

        val specie = Specie(id, name, desc, image, ship, imgShip, skill, star, color, origin)
        return specie
    }

    fun insertSpecies(specie: Specie) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(SpecieHelper.COLUMN_NAME, specie.name)
            put(SpecieHelper.COLUMN_DESC, specie.desc)
            put(SpecieHelper.COLUMN_IMAGE, specie.image)
            put(SpecieHelper.COLUMN_SHIP, specie.ship)
            put(SpecieHelper.COLUMN_IMG_SHIP, specie.imgShip)
            put(SpecieHelper.COLUMN_SKILL, specie.skill)
            put(SpecieHelper.COLUMN_STAR, specie.star)
            put(SpecieHelper.COLUMN_COLOR, specie.color)
            put(SpecieHelper.COLUMN_ORIGIN, specie.origin)
        }
        db.insert(SpecieHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllSpecies(): List<Specie> {
        val db = readableDatabase
        val specieList = mutableListOf<Specie>()
        val query = "SELECT * FROM species"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            specieList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return specieList
    }

    fun getSpecieById(specieId: Int): Specie {
        val db = readableDatabase
        val query = "SELECT * from species WHERE id = $specieId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
            val specie = getColumns(cursor)
        cursor.close()
        db.close()
        return specie
    }


}