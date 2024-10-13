package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.PlanetBuilds
import com.delek.species.database.dataclass.Prod
import com.delek.species.database.helper.BuildHelper.Companion.COLUMN_ID
import com.delek.species.database.helper.ProdHelper
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.PlanetBuildsHelper

class ProdDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    val db = readableDatabase
    val data = context.getSharedPreferences("data", Context.MODE_PRIVATE)
    fun insertProd(prod: Prod){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(ProdHelper.COLUMN_ID, prod.id)
            put(ProdHelper.COLUMN_TYPE, prod.type)
            put(ProdHelper.COLUMN_TYPE_ID, prod.typeId)
            put(ProdHelper.COLUMN_PLANET, prod.planet)
            put(ProdHelper.COLUMN_OWNER, prod.owner)
            put(ProdHelper.COLUMN_DAYS, prod.days)
        }
        db.insert(ProdHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getPlanetBuildProd(planetId: Int): Prod {
        var prod = Prod()
        val specie = data.getInt("specie", 0)
        val query = "SELECT * FROM prod WHERE type = 1 AND planet = $planetId AND owner = $specie"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_ID))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_TYPE))
            val typeId = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_TYPE_ID))
            val planet = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_PLANET))
            val owner = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_OWNER))
            val days = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_DAYS))

            prod = Prod(id, type, typeId, planet, owner, days)
        }
        cursor.close()
        db.close()
        return prod
    }

    fun deleteProd(id: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(id.toString())
        db.delete("prod", whereClause, whereArgs);
        db.close()
    }

    fun getMinProd(): Prod {
        var prod = Prod()
        val query = "SELECT *, MIN(days) FROM prod"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_ID))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_TYPE))
            val typeId = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_TYPE_ID))
            val planet = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_PLANET))
            val owner = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_OWNER))
            val days = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_DAYS))

            prod = Prod(id, type, typeId, planet, owner, days)
        }
        cursor.close()
        db.close()
        return prod
    }

    fun getALLProd(): List<Prod> {
        var prodList = mutableListOf<Prod>()
        val query = "SELECT * FROM prod"
        val cursor = db.rawQuery(query, null)
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_ID))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_TYPE))
            val typeId = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_TYPE_ID))
            val owner = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_OWNER))
            val days = cursor.getInt(cursor.getColumnIndexOrThrow(ProdHelper.COLUMN_DAYS))

            val prod = Prod(id, type, typeId, owner, days)
            prodList.add(prod)
        }
        cursor.close()
        db.close()
        return prodList
    }

    fun decrementDays(prod: Prod) {
        val values = ContentValues()
        values.put("days", prod.days - 1)
        db.update("prod", values, "id=${prod.id}", null)
        db.close()
    }


}
