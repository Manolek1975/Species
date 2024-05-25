package com.delek.species.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBSpeciesHelper(context: Context?) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // If you change the database schema, you must increment the database version.
    companion object{
        const val DATABASE_NAME: String = "db_species"
        const val DATABASE_VERSION: Int = 1
        const val TABLE_NAME: String = "species"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_DESC: String = "description"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_SKILL: String = "skill"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_STAR: String = "star"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableSpecies = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY_KEY,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_DESC TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_SKILL TEXT,")
            append("$COLUMN_TYPE INTEGER,")
            append("$COLUMN_STAR TEXT)")
        }
        db?.execSQL(createTableSpecies)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertSpecies(specie: Specie) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID, specie.id)
            put(COLUMN_NAME, specie.name)
            put(COLUMN_DESC, specie.desc)
            put(COLUMN_IMAGE, specie.image)
            put(COLUMN_SKILL, specie.skill)
            put(COLUMN_TYPE, specie.type)
            put(COLUMN_STAR, specie.star)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllSpecies(): List<Specie> {
        val specieList = mutableListOf<Specie>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
            val skill = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKILL))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
            val star = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STAR))

            val specie = Specie(id, name, desc, image, skill, type, star)
            specieList.add(specie)
        }

        cursor.close()
        db.close()
        return specieList
    }

    fun getSpecieById(specieId: Int): Specie{
        val db = readableDatabase
        val query = "SELECT * from $TABLE_NAME WHERE $COLUMN_ID = $specieId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
        val desc = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESC))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
        val skill = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SKILL))
        val type = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
        val star = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STAR))

        cursor.close()
        db.close()
        return Specie(id, name, desc, image, skill, type, star)
    }

    fun deleteSpecies(){
        val db = writableDatabase
        db.execSQL("DELETE from species")
        db.close()
    }


}