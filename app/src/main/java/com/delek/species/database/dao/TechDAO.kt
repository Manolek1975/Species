package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.PlanetBuilds
import com.delek.species.database.dataclass.Specie
import com.delek.species.database.dataclass.Tech
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.TechHelper
import com.delek.species.database.helper.TechLearnedHelper

class TechDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }


    fun insertTechs(tech: Tech){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TechHelper.COLUMN_NAME, tech.name)
            put(TechHelper.COLUMN_IMAGE, tech.image)
            put(TechHelper.COLUMN_REQUIRE, tech.require)
            put(TechHelper.COLUMN_UNLOCK, tech.unlock)
            put(TechHelper.COLUMN_COST, tech.cost)
            put(TechHelper.COLUMN_BUILD, tech.build)
            put(TechHelper.COLUMN_ORBITAL, tech.orbital)
            put(TechHelper.COLUMN_DEVICE, tech.device)

        }
        db.insert(TechHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertTechsLearned(specie: Specie, tech: Int){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TechLearnedHelper.COLUMN_SPECIE_ID, specie.id)
            put(TechLearnedHelper.COLUMN_TECH_ID, tech)
        }
        db.insert(TechLearnedHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getTechsBySpecie(specieId: Int): List<Tech> {
        val db = readableDatabase
        val techList = mutableListOf<Tech>()
        val query = "SELECT techs.* FROM techs INNER JOIN tech_learned " +
                "ON techs.id = tech_learned.tech_id " +
                "WHERE tech_learned.specie_id = $specieId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            techList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return techList
    }

    fun getTechsLearned(): List<Tech> {
        val techList = mutableListOf<Tech>()
        val db = readableDatabase
        val query = "SELECT * FROM techs WHERE required = 0"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            techList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return techList
    }

    private fun getColumns(cursor: Cursor): Tech {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(TechHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(TechHelper.COLUMN_NAME))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(TechHelper.COLUMN_IMAGE))
        val cost = cursor.getInt(cursor.getColumnIndexOrThrow(TechHelper.COLUMN_COST))
        val require = cursor.getString(cursor.getColumnIndexOrThrow(TechHelper.COLUMN_REQUIRE))
        val unlock = cursor.getString(cursor.getColumnIndexOrThrow(TechHelper.COLUMN_UNLOCK))

        val tech = Tech(id, name, image, cost, require.toInt(), unlock.toInt())
        return tech
    }


}