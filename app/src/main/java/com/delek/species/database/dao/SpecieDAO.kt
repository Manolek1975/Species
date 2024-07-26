package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Specie
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.PlanetsHelper
import com.delek.species.database.helper.SpeciesHelper

class SpecieDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {

    fun insertSpecies(specie: Specie) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(SpeciesHelper.COLUMN_ID, specie.id)
            put(SpeciesHelper.COLUMN_NAME, specie.name)
            put(SpeciesHelper.COLUMN_DESC, specie.desc)
            put(SpeciesHelper.COLUMN_IMAGE, specie.image)
            put(SpeciesHelper.COLUMN_SKILL, specie.skill)
            put(SpeciesHelper.COLUMN_TYPE, specie.type)
            put(SpeciesHelper.COLUMN_STAR, specie.star)
        }
        db.insert(SpeciesHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllSpecies(): List<Specie> {
        val specieList = mutableListOf<Specie>()
        val db = readableDatabase
        val query = "SELECT * FROM ${SpeciesHelper.TABLE_NAME}"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_NAME))
            val desc = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_DESC))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_IMAGE))
            val skill = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_SKILL))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_TYPE))
            val star = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_STAR))

            val specie = Specie(id, name, desc, image, skill, type, star)
            specieList.add(specie)
        }
        cursor.close()
        db.close()
        return specieList
    }

    fun getSpecieById(specieId: Int): Specie {
        val db = readableDatabase
        val query = "SELECT * from ${SpeciesHelper.TABLE_NAME} WHERE ${SpeciesHelper.COLUMN_ID} = $specieId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_NAME))
        val desc = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_DESC))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_IMAGE))
        val skill = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_SKILL))
        val type = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_TYPE))
        val star = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_STAR))

        cursor.close()
        db.close()
        return Specie(id, name, desc, image, skill, type, star)
    }

     override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}