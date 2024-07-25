package com.delek.species.database

import android.content.ContentValues
import android.content.Context
import android.database.DatabaseUtils
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.SpeciesHelper.Companion.TABLE_NAME


class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME: String = "db_species"
        const val DATABASE_VERSION: Int = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SpeciesHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(StarsHelper.SQL_CREATE_ENTRIES)
        db?.execSQL(PlanetsHelper.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SpeciesHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(StarsHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(PlanetsHelper.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    fun insertSpecies(specie: Specie) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(SpeciesHelper.COLUMN_ID, specie.id)
            put(SpeciesHelper.COLUMN_NAME, specie.name)
            put(SpeciesHelper.COLUMN_DESC, specie.desc)
            put(SpeciesHelper.COLUMN_IMAGE, specie.image)
            put(SpeciesHelper.COLUMN_SKILL, specie.skill)
            put(SpeciesHelper.COLUMN_TYPE, specie.type)
            put(SpeciesHelper.COLUMN_STAR, specie.star)
        }
        db.insert(TABLE_NAME, null, values)
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

    fun getAllSpecies(): List<Specie> {
        val specieList = mutableListOf<Specie>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_NAME))
            val desc = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_DESC))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_IMAGE))
            val skill = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_SKILL))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_TYPE))
            val star = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_STAR))

            val specie = Specie(id, name, desc, image, skill, type, star)
            specieList.add(specie)
        }
        cursor.close()
        db.close()
        return specieList
    }

    fun getAllStars(): List<Star>{
        val starList = mutableListOf<Star>()
        val db = readableDatabase
        val query = "SELECT * FROM stars"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_IMAGE))
            val sector = cursor.getString(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_SECTOR))
            val jumps = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_JUMPS))
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_Y))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_TYPE))
            val explore = cursor.getInt(cursor.getColumnIndexOrThrow(StarsHelper.COLUMN_EXPLORE))

            val star = Star(id, name, image, sector, jumps, x, y, type, explore)
            starList.add(star)
        }
        cursor.close()
        db.close()
        return starList
    }

    fun getAllPlanets(): List<Planet> {
        val planetList = mutableListOf<Planet>()
        val db = readableDatabase
        val query = "SELECT * FROM planets"
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

    fun getSpecieById(specieId: Int): Specie{
        val db = readableDatabase
        val query = "SELECT * from $TABLE_NAME WHERE ${SpeciesHelper.COLUMN_ID} = $specieId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_NAME))
        val desc = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_DESC))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_IMAGE))
        val skill = cursor.getString(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_SKILL))
        val type = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_TYPE))
        val star = cursor.getInt(cursor.getColumnIndexOrThrow(SpeciesHelper.COLUMN_STAR))

        cursor.close()
        db.close()
        return Specie(id, name, desc, image, skill, type, star)
    }

    fun isEmpty(table: String?): Boolean {
        val database = this.readableDatabase
        val numRows = DatabaseUtils.queryNumEntries(database, table)

        return numRows == 0L
    }



}