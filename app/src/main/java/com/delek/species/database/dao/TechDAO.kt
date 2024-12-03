package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.model.Tech
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.TechHelper
import com.delek.species.database.helper.TechLearnedHelper

class TechDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    val data: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)

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

    fun insertTechs(tech: Tech){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TechHelper.COLUMN_NAME, tech.name)
            put(TechHelper.COLUMN_IMAGE, tech.image)
            put(TechHelper.COLUMN_COST, tech.cost)
            put(TechHelper.COLUMN_REQUIRE, tech.require)
            put(TechHelper.COLUMN_UNLOCK, tech.unlock)
        }
        db.insert(TechHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertTechsLearned(specieId: Int, tech: Int){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TechLearnedHelper.COLUMN_SPECIE_ID, specieId)
            put(TechLearnedHelper.COLUMN_TECH_ID, tech)
            put(TechLearnedHelper.COLUMN_LEARNED, 0)
        }
        db.insert(TechLearnedHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getTechById(typeId: Int): Tech {
        val db = readableDatabase
        val query = "SELECT * FROM techs WHERE id = $typeId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToNext()
            val tech = getColumns(cursor)
        cursor.close()
        db.close()
        return tech
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

    fun getTechLearned(techId: Int): Int {
        val specie = data.getInt("specie", 0)
        val db = readableDatabase
        val query = "SELECT * FROM tech_learned WHERE specie_id=$specie AND tech_id = $techId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
            val learned = cursor.getInt(cursor.getColumnIndexOrThrow(TechLearnedHelper.COLUMN_ID))
        cursor.close()
        db.close()
        return learned
    }

    fun setLearned(id: Int) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("learned", 1)
        db.update("tech_learned", values, "id=$id", null)
        db.close()

    }

    fun isLearned(techId: Int): Boolean {
        val specie = data.getInt("specie", 0)
        val db = readableDatabase
        val query = "SELECT * FROM tech_learned WHERE specie_id=$specie AND tech_id = $techId AND learned = 1"
        val cursor = db.rawQuery(query, null)
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }


}