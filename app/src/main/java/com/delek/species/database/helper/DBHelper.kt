package com.delek.species.database.helper

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.Device
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.Ship
import com.delek.species.database.dataclass.Specie
import com.delek.species.database.dataclass.Star
import com.delek.species.database.dataclass.Tech


class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME: String = "db_species"
        const val DATABASE_VERSION: Int = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SpecieHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(StarHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(PlanetHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(BuildHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(TechHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(PlanetBuildsHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(ShipHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(DeviceHelper.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SpecieHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(StarHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlanetHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(BuildHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(TechHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlanetBuildsHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(ShipHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(DeviceHelper.SQL_DELETE_ENTRIES)
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
            put(SpecieHelper.COLUMN_NAME, specie.name)
            put(SpecieHelper.COLUMN_DESC, specie.desc)
            put(SpecieHelper.COLUMN_IMAGE, specie.image)
            put(SpecieHelper.COLUMN_SKILL, specie.skill)
            put(SpecieHelper.COLUMN_TYPE, specie.type)
            put(SpecieHelper.COLUMN_STAR, specie.star)
            put(SpecieHelper.COLUMN_COLOR, specie.color)
            put(SpecieHelper.COLUMN_ORIGIN, specie.origin)
        }
        db.insert(SpecieHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertStars(star: Star) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(StarHelper.COLUMN_ID, star.id)
            put(StarHelper.COLUMN_NAME, star.name)
            put(StarHelper.COLUMN_IMAGE, star.image)
            put(StarHelper.COLUMN_SECTOR, star.sector)
            put(StarHelper.COLUMN_JUMPS, star.jumps)
            put(StarHelper.COLUMN_X, star.x)
            put(StarHelper.COLUMN_Y, star.y)
            put(StarHelper.COLUMN_TYPE, star.type)
            put(StarHelper.COLUMN_EXPLORE, star.explore)
        }
        db.insert(StarHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertPlanets(planet: Planet){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(PlanetHelper.COLUMN_STAR, planet.star)
            put(PlanetHelper.COLUMN_NAME, planet.name)
            put(PlanetHelper.COLUMN_IMAGE, planet.image)
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
        val values = ContentValues().apply {
            put(PlanetBuildsHelper.COLUMN_PLANET_ID, planet.id)
            put(PlanetBuildsHelper.COLUMN_BUILD_ID, build.id)
            put(PlanetBuildsHelper.COLUMN_LEVEL, 1)
            put(PlanetBuildsHelper.COLUMN_DAYSLEFT, build.cost)
        }
        db.insert(PlanetBuildsHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertShips(ship: Ship) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(ShipHelper.COLUMN_NAME, ship.name)
            put(ShipHelper.COLUMN_SPECIE_ID, ship.specieId)
            put(ShipHelper.COLUMN_ORBIT, ship.orbit)
            put(ShipHelper.COLUMN_ROUTE, ship.route)
            put(ShipHelper.COLUMN_DAYS, ship.days)
        }
        db.insert(ShipHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertDevices(device: Device){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(DeviceHelper.COLUMN_NAME, device.name)
            put(DeviceHelper.COLUMN_DESC, device.desc)
            put(DeviceHelper.COLUMN_IMAGE, device.image)
            put(DeviceHelper.COLUMN_TYPE, device.type)
            put(DeviceHelper.COLUMN_COST, device.cost)
            put(DeviceHelper.COLUMN_POWER, device.power)
            put(DeviceHelper.COLUMN_TECH_ID, device.techId)
        }
        db.insert(DeviceHelper.TABLE_NAME, null, values)
        db.close()
    }

}