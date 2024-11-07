package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
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
        }
        db.insert(PlanetBuildsHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getPlanetBuildsByPlanet(id: Int): List<PlanetBuilds> {
        val db = readableDatabase
        val planetBuildList = mutableListOf<PlanetBuilds>()
        val query = "SELECT * FROM planet_builds WHERE planet_id = $id"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            planetBuildList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return planetBuildList
    }

    fun getPlanetBuildById(build: Int, planet: Planet): PlanetBuilds {
        val db = readableDatabase
        var planetBuild = PlanetBuilds()
        val query = "SELECT * FROM planet_builds WHERE build_id = $build AND planet_id = ${planet.id}"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            planetBuild = getColumns(cursor)
        }
        cursor.close()
        db.close()
        return planetBuild
    }

    fun setBuildLevel(planetBuild: PlanetBuilds){
        val db = writableDatabase
        val values = ContentValues()
        values.put("level", planetBuild.level + 1)
        db.update("planet_builds", values, "id=${planetBuild.id}", null)
        db.close()
    }

    private fun getColumns(cursor: Cursor): PlanetBuilds {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_ID))
        val planetId = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_PLANET_ID))
        val buildId = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_BUILD_ID))
        val level = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_LEVEL))

        val planetBuild = PlanetBuilds(id, planetId, buildId, level)
        return planetBuild
    }
}