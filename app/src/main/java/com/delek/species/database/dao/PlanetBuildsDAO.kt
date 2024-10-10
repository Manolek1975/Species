package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.PlanetBuilds
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.PlanetBuildsHelper

class PlanetBuildsDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
)  {
    override fun onCreate(p0: SQLiteDatabase?) { }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

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

    fun getPlanetBuild(build: Int, planet: Planet): PlanetBuilds {
        var planetBuild = PlanetBuilds()
        val db=readableDatabase
        val query = "SELECT * FROM planet_builds WHERE build_id = $build AND planet_id = ${planet.id}"
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


    fun getBuildsUnderConstruction() : List<PlanetBuilds> {
        val planetBuildList = mutableListOf<PlanetBuilds>()
        val db = readableDatabase
        val query = "SELECT * FROM planet_builds WHERE days_left > -1"
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

    fun decrementDays(planetBuild: PlanetBuilds) {
        val db = readableDatabase
        val values = ContentValues()
        values.put("days_left", planetBuild.daysLeft - 1)
        db.update("planet_builds", values, "id=${planetBuild.id}", null)
        db.close()
    }

/*    fun getMinDaysLeft() : Int {
        val db = readableDatabase
        var minDaysLeft = 0
        val query = "SELECT days_left, MIN(days_left) FROM planet_builds GROUP BY days_left HAVING MIN(days_left) > -1"
        val cursor = db.rawQuery(query, null)
        if (cursor.count > 0) {
            cursor.moveToFirst()
            minDaysLeft = cursor.getInt(0)
        }
        cursor.close()
        return minDaysLeft
    }*/

    fun getMinBuild() : PlanetBuilds {
        val db = readableDatabase
        var planetBuild = PlanetBuilds()
        val query = "SELECT *, MIN(days_left) FROM planet_builds"
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
        return planetBuild
    }


}