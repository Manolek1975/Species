package com.delek.species.database.dao

import android.content.Context
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

    fun getBuildsByTech(techMax: Int): List<Build> {
        val buildList = mutableListOf<Build>()
        val db = readableDatabase
        val query = "SELECT * FROM ${BuildHelper.TABLE_NAME} WHERE tech <= $techMax"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
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

            val build = Build(id, name, desc, image, tech, cost, food, industry, science, population, offense, defense, invader)
            buildList.add(build)
        }
        cursor.close()
        db.close()
        return buildList
    }

    fun getBuildsByPlanet(planetBuild: List<PlanetBuilds>): List<Build> {
        val buildList = mutableListOf<Build>()
        val db = readableDatabase
        val query = "SELECT * FROM ${BuildHelper.TABLE_NAME} WHERE id IN (${planetBuild.joinToString { it.buildId.toString() }})"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
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

            val build = Build(id, name, desc, image, tech, cost, food, industry, science, population, offense, defense, invader)
            buildList.add(build)
        }
        cursor.close()
        db.close()
        return buildList

    }


    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}