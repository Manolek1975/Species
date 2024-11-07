package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Build
import com.delek.species.database.dataclass.PlanetBuilds
import com.delek.species.database.helper.BuildHelper
import com.delek.species.database.helper.DBHelper


class BuildDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }


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

    fun getBuildById(buildId: Int): Build {
        val db = readableDatabase
        var build = Build()
        val query = "SELECT * FROM ${BuildHelper.TABLE_NAME} WHERE id = $buildId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            build = getColumns(cursor)
        }
        cursor.close()
        db.close()
        return build
    }

    fun getBuildsByTech(techMax: Int): List<Build> {
        val db = readableDatabase
        val buildList = mutableListOf<Build>()
        val query = "SELECT * FROM ${BuildHelper.TABLE_NAME} WHERE tech <= $techMax"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            buildList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return buildList
    }

    fun getBuildsByPlanet(planetBuild: List<PlanetBuilds>): List<Build> {
        val db = readableDatabase
        val buildList = mutableListOf<Build>()
        val query = "SELECT * FROM ${BuildHelper.TABLE_NAME} WHERE id IN (${planetBuild.joinToString { it.buildId.toString() }})"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            buildList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return buildList
    }

    private fun getColumns(cursor: Cursor): Build {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_NAME))
        val desc = cursor.getString(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_DESC))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_IMAGE))
        val tech = cursor.getInt(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_TECH))
        val cost = cursor.getInt(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_COST))
        val food = cursor.getInt(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_FOOD))
        val industry = cursor.getInt(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_INDUSTRY))
        val science = cursor.getInt(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_SCIENCE))
        val population = cursor.getInt(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_POPULATION))
        val offense = cursor.getInt(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_OFFENCE))
        val defense = cursor.getInt(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_DEFENSE))
        val invader = cursor.getInt(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_INVADER))
        val orbital = cursor.getInt(cursor.getColumnIndexOrThrow(BuildHelper.COLUMN_ORBITAL))

        val build = Build(id, name, desc, image, tech, cost, food, industry, science, population, offense, defense, invader, orbital)
        return build
    }


}