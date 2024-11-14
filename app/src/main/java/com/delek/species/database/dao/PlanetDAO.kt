package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Planet
import com.delek.species.database.dataclass.PlanetTypes
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.PlanetHelper
import com.delek.species.database.helper.PlanetExploredHelper
import com.delek.species.database.helper.PlanetTypesHelper


class PlanetDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    val data: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    private fun getColumns(cursor: Cursor): Planet {
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
        val research = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_RESEARCH))
        val defense = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_DEFENSE))
        val population = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetHelper.COLUMN_POPULATION))

        val planet = Planet(id, star, name, image, position, size, type, owner,
            food, production, research, defense, population)
        return planet
    }

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
            put(PlanetHelper.COLUMN_DEFENSE, planet.defense)
            put(PlanetHelper.COLUMN_RESEARCH, planet.research)
            put(PlanetHelper.COLUMN_POPULATION, planet.population)
        }
        db.insert(PlanetHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertPlanetTypes(types: PlanetTypes) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(PlanetTypesHelper.COLUMN_NAME, types.name)
            put(PlanetTypesHelper.COLUMN_FOOD, types.food)
            put(PlanetTypesHelper.COLUMN_PROD, types.prod)
            put(PlanetTypesHelper.COLUMN_TECH, types.tech)
        }
        db.insert(PlanetTypesHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertPlanetExplored(specieId: Int, planetId: Int) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(PlanetExploredHelper.COLUMN_SPECIE_ID, specieId)
            put(PlanetExploredHelper.COLUMN_PLANET_ID, planetId)
            put(PlanetExploredHelper.COLUMN_EXPLORED, 1)
        }
        db.insert(PlanetExploredHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getType(typeId: Int): PlanetTypes {
        val db = readableDatabase
        var type = PlanetTypes()
        val query = "SELECT * FROM planet_types WHERE id = $typeId"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetTypesHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(PlanetTypesHelper.COLUMN_NAME))
            val food = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetTypesHelper.COLUMN_FOOD))
            val prod = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetTypesHelper.COLUMN_PROD))
            val tech = cursor.getInt(cursor.getColumnIndexOrThrow(PlanetTypesHelper.COLUMN_TECH))

            type = PlanetTypes(id, name, food, prod, tech)
        }
        cursor.close()
        db.close()
        return type
    }

    fun getPlanetsByStarId(starId: Int?): List<Planet> {
        val db = readableDatabase
        val planetList = mutableListOf<Planet>()
        val query = "SELECT * FROM planets WHERE star = $starId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            planetList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return planetList
    }

    fun getPlanetById(planetId: Int?): Planet {
        val db = readableDatabase
        var planet = Planet()
        val query = "SELECT * FROM planets where id = $planetId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            planet = getColumns(cursor)
        }
        cursor.close()
        db.close()
        return planet
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
        db.close()
        return planetName
    }

    fun getPlanetsExploredBySpecie(specieId: Int): List<Planet> {
        val db = readableDatabase
        val planetList = mutableListOf<Planet>()
        val query = "SELECT planets.* FROM planets INNER JOIN planet_explored " +
                "ON planets.id = planet_explored.planet_id " +
                "WHERE planet_explored.specie_id = $specieId " +
                "AND planet_explored.explored = 1"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            planetList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return planetList

    }

    fun getPlanetExplored(planetId: Int): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM planet_explored WHERE planet_id = $planetId AND explored = 1"
        val cursor = db.rawQuery(query, null)
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun getPlanetColony(planetId: Int): Boolean {
        val db = readableDatabase
        val query = "SELECT * FROM planet_explored WHERE planet_id = $planetId AND colony = 1"
        val cursor = db.rawQuery(query, null)
        val exists = cursor.count > 0
        cursor.close()
        db.close()
        return exists
    }

    fun setPlanetColony(planet: Planet, specieId: Int){
        val type = getType(planet.type)
        val db = writableDatabase
        val values = ContentValues()
        values.put("specie_id", specieId)
        values.put("colony", 1)
        db.update("planet_explored", values, "planet_id=${planet.id}", null)
        values.clear()
        values.put("owner", specieId)
        values.put("food", type.food)
        values.put("production", type.prod)
        values.put("population", 20)
        db.update("planets", values, "id=${planet.id}", null)
        db.close()
    }

    fun setPlanetResources(id: Int, res: MutableMap<String, Int>){
        val db = writableDatabase
        val values = ContentValues()
        values.put("food", res["food"])
        values.put("production", res["prod"])
        values.put("research", res["res"])
        values.put("population", res["pop"])
        db.update("planets", values, "id=$id", null)
        db.close()
    }

    fun decrementPopulation(planet: Planet) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("population", planet.population -1)
        db.update("planets", values, "id=${planet.id}", null)
        db.close()
    }

    fun getOriginPlanet(id: Int): Planet {
        val db = readableDatabase
        var planet = Planet()
        val query = "SELECT * FROM planets WHERE star = $id AND position = 2"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            planet = getColumns(cursor)
        }
        cursor.close()
        db.close()
        return planet
    }

}