package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.PlanetBuilds
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.PlanetHelper
import com.delek.species.database.helper.PlanetBuildsHelper

class PlanetDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    fun insertPlanets(planet: Planet){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(PlanetHelper.COLUMN_STAR, planet.star)
            put(PlanetHelper.COLUMN_NAME, planet.name)
            put(PlanetHelper.COLUMN_IMAGE, planet.image)
            put(PlanetHelper.COLUMN_POSITION, planet.position)
            put(PlanetHelper.COLUMN_SIZE, planet.size)
            put(PlanetHelper.COLUMN_TYPE, planet.type)
            put(PlanetHelper.COLUMN_OWNER, planet.owner)
            put(PlanetHelper.COLUMN_FOOD, planet.food)
            put(PlanetHelper.COLUMN_PRODUCTION, planet.production)
            put(PlanetHelper.COLUMN_POPULATION, planet.population)
            put(PlanetHelper.COLUMN_RESEARCH, planet.research)
            put(PlanetHelper.COLUMN_EXPLORE, planet.explore)
        }
        db.insert(PlanetHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertPlanetBuild(build: Build, planet: Planet) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(PlanetBuildsHelper.COLUMN_PLANET_ID, planet.id)
            put(PlanetBuildsHelper.COLUMN_BUILD_ID, build.id)
            put(PlanetBuildsHelper.COLUMN_LEVEL, 1)
            put(PlanetBuildsHelper.COLUMN_DAYSLEFT, build.cost)
        }
        db.insert(PlanetBuildsHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getPlanetsByStarId(starId: Int?): List<Planet> {
        val planetList = mutableListOf<Planet>()
        val db = readableDatabase
        val query = "SELECT * FROM planets WHERE star = $starId"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_ID))
            val star = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_STAR))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_IMAGE))
            val position = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_POSITION))
            val size = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_SIZE))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_TYPE))
            val owner = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_OWNER))
            val food = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_FOOD))
            val production = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_PRODUCTION))
            val population = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_POPULATION))
            val research = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_RESEARCH))
            val explore = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_EXPLORE))

            val planet = Planet(id, star, name, image, position, size, type, owner, food, production, population, research, explore)
            planetList.add(planet)
        }
        cursor.close()
        db.close()
        return planetList
    }

    fun getPlanetById(planetId: Int?): Planet {
        var planet = Planet()
        val db = readableDatabase
        val query = "SELECT * FROM planets where id = $planetId"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_ID))
            val star = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_STAR))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_IMAGE))
            val position = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_POSITION))
            val size = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_SIZE))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_TYPE))
            val owner = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_OWNER))
            val food = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_FOOD))
            val production = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_PRODUCTION))
            val population = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_POPULATION))
            val research = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_RESEARCH))
            val explore = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_EXPLORE))

            planet = Planet(id, star, name, image, position, size, type, owner, food, production, population, research, explore)
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

    fun getPlanetBuild(build: Build, planet: Planet): PlanetBuilds {
        var planetBuild = PlanetBuilds()
        val db=readableDatabase
        val query = "SELECT * FROM planet_builds WHERE build_id = ${build.id} AND planet_id = ${planet.id}"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_ID))
            val planetId = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_PLANET_ID))
            val buildId = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_BUILD_ID))
            val level = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_LEVEL))
            val daysLeft = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_DAYSLEFT))

            planetBuild = PlanetBuilds(id, planetId, buildId, level, daysLeft)
        }
        cursor.close()
        db.close()
        return planetBuild
    }

    fun getAllPlanetBuilds(planet: Planet): List<PlanetBuilds> {
        val planetBuildList = mutableListOf<PlanetBuilds>()
        val db = readableDatabase
        val query = "SELECT * FROM planet_builds WHERE planet_id = ${planet.id}"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_ID))
            val planetId = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_PLANET_ID))
            val buildId = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_BUILD_ID))
            val level = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_LEVEL))
            val daysLeft = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_DAYSLEFT))

            val planetBuild = PlanetBuilds(id, planetId, buildId, level, daysLeft)
            planetBuildList.add(planetBuild)
        }
        cursor.close()
        db.close()
        return planetBuildList
    }

    fun setPlanetBuild(planetBuild: PlanetBuilds){
        val db = readableDatabase
        val values = ContentValues()
        values.put("level", planetBuild.level + 1)
        db.update("planet_builds", values, "id=${planetBuild.id}", null)
        db.close()
    }

    fun getPlanetName(planetId: Int): Any {
        val db = readableDatabase
        var planetName = ""
        val query = "SELECT name FROM planets WHERE id = $planetId"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            planetName = cursor.getString(cursor.getColumnIndexOrThrow("name"))
        }
        cursor.close()
        return planetName
    }
}