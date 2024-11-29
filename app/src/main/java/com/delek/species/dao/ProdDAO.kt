package com.delek.species.dao

import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.model.Prod
import com.delek.species.database.model.Tech
import com.delek.species.database.helper.BuildHelper.Companion.COLUMN_ID
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.ProdHelper

class ProdDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    val data: SharedPreferences = context.getSharedPreferences("data", Context.MODE_PRIVATE)

    private fun getColumns(cursor: Cursor): Prod {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_ID))
        val type = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_TYPE))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_NAME))
        val typeId = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_TYPE_ID))
        val planet = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_PLANET))
        val owner = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_OWNER))
        val days = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_DAYS))

        val prod = Prod(id, type, name, typeId, planet, owner, days)
        return prod
    }

    fun insertProd(prod: Prod){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(ProdHelper.COLUMN_TYPE, prod.type)
            put(ProdHelper.COLUMN_NAME, prod.name)
            put(ProdHelper.COLUMN_TYPE_ID, prod.typeId)
            put(ProdHelper.COLUMN_PLANET, prod.planet)
            put(ProdHelper.COLUMN_OWNER, prod.owner)
            put(ProdHelper.COLUMN_DAYS, prod.days)
        }
        db.insert(ProdHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertProdTech(tech: Tech) {
        val science = data.getInt("science", 0)
        val db = writableDatabase
        val specie = data.getInt("specie", 0)
        val values = ContentValues().apply {
            put(ProdHelper.COLUMN_TYPE, 3)
            put(ProdHelper.COLUMN_NAME, tech.name)
            put(ProdHelper.COLUMN_TYPE_ID, tech.id)
            put(ProdHelper.COLUMN_PLANET, 0)
            put(ProdHelper.COLUMN_OWNER, specie)
            put(ProdHelper.COLUMN_DAYS, tech.cost / science)
        }
        db.insert(ProdHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getPlanetProd(planetId: Int): Prod {
        val db = readableDatabase
        var prod = Prod()
        val specie = data.getInt("specie", 0)
        val query = "SELECT * FROM prod WHERE planet = $planetId AND owner = $specie"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            prod = getColumns(cursor)
        }
        cursor.close()
        db.close()
        return prod
    }

    fun getMinProd(): Prod {
        val db = readableDatabase
        var prod = Prod()
        val query = "SELECT *, MIN(days) FROM prod"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            prod = getColumns(cursor)
        }
        cursor.close()
        db.close()
        return prod
    }

    fun getALLProd(): List<Prod> {
        val db = readableDatabase
        val prodList = mutableListOf<Prod>()
        val query = "SELECT * FROM prod"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            prodList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return prodList
    }

    fun getDaysLeft(shipId: Int): Int {
        val db = readableDatabase
        var days = 0
        val query = "SELECT * FROM prod WHERE type = 2 AND type_id = $shipId"
        val cursor = db.rawQuery(query, null)
        if(cursor.count > 0) {
            cursor.moveToFirst()
            days = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_DAYS))
        }
        cursor.close()
        db.close()
        return days
    }

    fun decrementDays(prod: Prod) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("days", prod.days - 1)
        db.update("prod", values, "id=${prod.id}", null)
        db.close()
    }

    fun deleteProd(id: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(id.toString())
        db.delete("prod", whereClause, whereArgs)
        db.close()
    }

    fun getBuildProd(): Boolean {
        val db = readableDatabase
        var prod = false
        val specie = data.getInt("specie", 0)
        val query = "SELECT * FROM prod WHERE type = 1 AND owner = $specie"
        val cursor = db.rawQuery(query, null)
        if (cursor.count == 0) {
            prod = true
        }
        cursor.close()
        db.close()
        return prod
    }




}
