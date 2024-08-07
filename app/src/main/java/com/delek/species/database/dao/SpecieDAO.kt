package com.delek.species.database.dao

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Specie
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.SpecieHelper

class SpecieDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {


    fun getAllSpecies(): List<Specie> {
        val specieList = mutableListOf<Specie>()
        val db = readableDatabase
        val query = "SELECT * FROM species"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_NAME))
            val desc = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_DESC))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_IMAGE))
            val skill = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_SKILL))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_TYPE))
            val star = cursor.getInt(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_STAR))
            val color = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_COLOR))
            val origin = cursor.getInt(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_ORIGIN))

            val specie = Specie(id, name, desc, image, skill, type, star, color, origin)
            specieList.add(specie)
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

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_NAME))
        val desc = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_DESC))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_IMAGE))
        val skill = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_SKILL))
        val type = cursor.getInt(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_TYPE))
        val star = cursor.getInt(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_STAR))
        val color = cursor.getString(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_COLOR))
        val origin = cursor.getInt(cursor.getColumnIndexOrThrow(SpecieHelper.COLUMN_ORIGIN))

        cursor.close()
        db.close()
        return Specie(id, name, desc, image, skill, type, star, color, origin)
    }

     override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}