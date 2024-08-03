package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.PlanetsHelper

class PlanetDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {


    fun getPlanetsByStarId(starId: Int?): List<Planet> {
        val planetList = mutableListOf<Planet>()
        val db = readableDatabase
        val query = "SELECT * FROM planets WHERE star = $starId"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_ID))
            val star = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_STAR))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_IMAGE))
            val size = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_SIZE))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_TYPE))
            val owner = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_OWNER))
            val food = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_FOOD))
            val production = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_PRODUCTION))
            val population = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_POPULATION))
            val research = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_RESEARCH))
            val explore = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_EXPLORE))

            val planet = Planet(id, star, name, image, size, type, owner, food, production, population, research, explore)
            planetList.add(planet)
        }
        cursor.close()
        db.close()
        return planetList
    }

    fun getPlanetById(planetId: Int?): Planet {
        //val planet = mutableListOf<Planet>()
        var planet = Planet()
        val db = readableDatabase
        val query = "SELECT * FROM planets where id = $planetId"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_ID))
            val star = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_STAR))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_IMAGE))
            val size = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_SIZE))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_TYPE))
            val owner = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_OWNER))
            val food = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_FOOD))
            val production = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_PRODUCTION))
            val population = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_POPULATION))
            val research = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_RESEARCH))
            val explore = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetsHelper.COLUMN_EXPLORE))

            planet = Planet(id, star, name, image, size, type, owner, food, production, population, research, explore)
        //planetList.add(planet)
        }
        cursor.close()
        db.close()
        return planet
    }

    fun setPlanetExplored(id: Int){
        val db = readableDatabase
        val values = ContentValues()
        values.put("explore", 1)
        db.update("planets", values, "id=$id", null)
        db.close()
    }
    fun setPlanetColonized(id: Int){
        val db = readableDatabase
        val values = ContentValues()
        values.put("explore", 2)
        db.update("planets", values, "id=$id", null)
        db.close()
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}