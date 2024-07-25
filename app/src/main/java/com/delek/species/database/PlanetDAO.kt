package com.delek.species.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class PlanetDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {

    fun insertPlanets(planet: Planet){
        val db = writableDatabase
        val values = ContentValues().apply {
            //put(PlanetHelper.COLUMN_ID, planet.id)
            put(PlanetsHelper.COLUMN_STAR, planet.star)
            put(PlanetsHelper.COLUMN_NAME, planet.name)
            put(PlanetsHelper.COLUMN_IMAGE, planet.image)
            put(PlanetsHelper.COLUMN_SIZE, planet.size)
            put(PlanetsHelper.COLUMN_TYPE, planet.type)
            put(PlanetsHelper.COLUMN_OWNER, planet.owner)
            put(PlanetsHelper.COLUMN_FOOD, planet.food)
            put(PlanetsHelper.COLUMN_PRODUCTION, planet.production)
            put(PlanetsHelper.COLUMN_POPULATION, planet.population)
            put(PlanetsHelper.COLUMN_RESEARCH, planet.research)
            put(PlanetsHelper.COLUMN_EXPLORE, planet.explore)
        }
        db.insert(PlanetsHelper.TABLE_NAME, null, values)
        db.close()
    }

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


    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}