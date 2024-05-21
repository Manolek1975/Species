package com.delek.species.database

import android.content.ContentValues
import android.content.Context
import android.content.res.Resources
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.R


class DBHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {


    private val context: Context? = null

    // If you change the database schema, you must increment the database version.
    companion object{
        const val DATABASE_NAME: String = "species_db"
        const val DATABASE_VERSION: Int = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DBSpecies.createTableSpecies)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL(DBSpecies.dropTableQuery)
        onCreate(db)
    }

    fun insertSpecies(specie : Specie){

        val res : Resources = context!!.resources

        val name = res.getStringArray(R.array.name_species)
        val image = res.getStringArray(R.array.image_species)
        val description = res.getStringArray(R.array.description_species)
        val star = res.getStringArray(R.array.origin_species)


        val db = writableDatabase
        val values = ContentValues().apply {
            put(DBSpecies.COLUMN_ID, specie.id)
            put(DBSpecies.COLUMN_NAME, specie.name)
            put(DBSpecies.COLUMN_DESC, specie.desc)
            put(DBSpecies.COLUMN_IMAGE, specie.image)
            put(DBSpecies.COLUMN_SKILL, specie.skill)
            put(DBSpecies.COLUMN_TYPE, specie.type)
            put(DBSpecies.COLUMN_STAR, specie.start)
        }
        db.insert(DBSpecies.TABLE_NAME, null, values)
        db.close()
    }


}