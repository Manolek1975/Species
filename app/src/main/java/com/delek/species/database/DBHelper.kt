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
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(SpeciesHelper.SQL_DELETE_ENTRIES)
        db?.execSQL(StarsHelper.SQL_DELETE_ENTRIES)
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

        return if (numRows == 0L) {
            true
        } else {
            false
        }
    }



}