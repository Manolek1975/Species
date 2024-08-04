package com.delek.species.database.helper

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Specie
import com.delek.species.database.dataclass.Star
import com.delek.species.database.dataclass.Tech


class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME: String = "db_species"
        const val DATABASE_VERSION: Int = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SpeciesHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(StarsHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(PlanetsHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(BuildHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(TechHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(PlanetBuildsHelper.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SpeciesHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(StarsHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlanetsHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(BuildHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlanetBuildsHelper.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun isEmpty(table: String?): Boolean {
        val database = this.readableDatabase
        val numRows = DatabaseUtils.queryNumEntries(database, table)

        return numRows == 0L
    }

    fun insertSpecies(specie: Specie) {
        val db = writableDatabase
        val values = ContentValues().apply {
            //put(SpeciesHelper.COLUMN_ID, specie.id)
            put(SpeciesHelper.COLUMN_NAME, specie.name)
            put(SpeciesHelper.COLUMN_DESC, specie.desc)
            put(SpeciesHelper.COLUMN_IMAGE, specie.image)
            put(SpeciesHelper.COLUMN_SKILL, specie.skill)
            put(SpeciesHelper.COLUMN_TYPE, specie.type)
            put(SpeciesHelper.COLUMN_STAR, specie.star)
            put(SpeciesHelper.COLUMN_COLOR, specie.color)
            put(SpeciesHelper.COLUMN_ORIGIN, specie.origin)
        }
        db.insert(SpeciesHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertStars(star: Star) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(StarsHelper.COLUMN_ID, star.id)
            put(StarsHelper.COLUMN_NAME, star.name)
            put(StarsHelper.COLUMN_IMAGE, star.image)
            put(StarsHelper.COLUMN_SECTOR, star.sector)
            put(StarsHelper.COLUMN_JUMPS, star.jumps)
            put(StarsHelper.COLUMN_X, star.x)
            put(StarsHelper.COLUMN_Y, star.y)
            put(StarsHelper.COLUMN_TYPE, star.type)
            put(StarsHelper.COLUMN_EXPLORE, star.explore)
        }
        db.insert(StarsHelper.TABLE_NAME, null, values)
        db.close()
    }

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

    fun insertBuilds(build: Build){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(BuildHelper.COLUMN_NAME, build.name)
            put(BuildHelper.COLUMN_DESC, build.description)
            put(BuildHelper.COLUMN_IMAGE, build.image)
            put(BuildHelper.COLUMN_TECH, build.tech)
            put(BuildHelper.COLUMN_COST, build.cost)
            put(BuildHelper.COLUMN_FOOD, build.food)
            put(BuildHelper.COLUMN_INDUSTRY, build.industry)
            put(BuildHelper.COLUMN_SCIENCE, build.science)
            put(BuildHelper.COLUMN_POPULATION, build.population)
            put(BuildHelper.COLUMN_OFFENCE, build.offense)
            put(BuildHelper.COLUMN_DEFENSE, build.defense)
            put(BuildHelper.COLUMN_INVADER, build.invader)
        }
        db.insert(BuildHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertTechs(tech: Tech){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(TechHelper.COLUMN_NAME, tech.name)
            put(TechHelper.COLUMN_COST, tech.cost)
            put(TechHelper.COLUMN_REQUIRE, tech.require)
            put(TechHelper.COLUMN_UNLOCK, tech.unlock)
        }
        db.insert(TechHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertPlanetBuild(build: Build, planet: Planet) {
        val db = writableDatabase
        val nivel = getBuildLevel(build, planet)
        val values = ContentValues().apply {
            put(PlanetBuildsHelper.COLUMN_PLANET_ID, planet.id)
            put(PlanetBuildsHelper.COLUMN_BUILD_ID, build.id)
            put(PlanetBuildsHelper.COLUMN_NIVEL, nivel + 1)
            put(PlanetBuildsHelper.COLUMN_DAYSLEFT, build.cost)
        }
        db.insert(PlanetBuildsHelper.TABLE_NAME, null, values)
        db.close()

    }

    fun getBuildLevel(build: Build, planet: Planet): Int {
        val db =readableDatabase
        val nivel: Int
        val query = "SELECT * FROM planet_builds WHERE build_id = ${build.id} AND planet_id = ${planet.id}"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            nivel = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetBuildsHelper.COLUMN_NIVEL))
        } else {
            nivel = 0
        }

        cursor.close()
        db.close()
        return nivel
    }

}