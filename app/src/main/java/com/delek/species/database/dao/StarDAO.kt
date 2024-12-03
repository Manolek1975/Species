package com.delek.species.database.dao


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.model.Star
import com.delek.species.database.model.StarExplored
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.StarExploredHelper
import com.delek.species.database.helper.StarHelper


class StarDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) { }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) { }

    private fun getColumns(cursor: Cursor): Star {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_NAME))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_IMAGE))
        val sector = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_SECTOR))
        val jumps = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_JUMPS))
        val x = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_X))
        val y = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_Y))
        val type = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_TYPE))
        val explore = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_EXPLORE))
        val owner = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_OWNER))

        val star = Star(id, name, image, sector, jumps, x, y, type, explore, owner)
        return star
    }

    fun insertStars(star: Star) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(StarHelper.COLUMN_NAME, star.name)
            put(StarHelper.COLUMN_IMAGE, star.image)
            put(StarHelper.COLUMN_SECTOR, star.sector)
            put(StarHelper.COLUMN_JUMPS, star.jumps)
            put(StarHelper.COLUMN_X, star.x)
            put(StarHelper.COLUMN_Y, star.y)
            put(StarHelper.COLUMN_TYPE, star.type)
            put(StarHelper.COLUMN_EXPLORE, star.explore)
            put(StarHelper.COLUMN_OWNER, star.owner)
        }
        db.insert(StarHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun insertStarExplored(star: StarExplored) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(StarExploredHelper.COLUMN_SPECIE_ID, star.specieId)
            put(StarExploredHelper.COLUMN_STAR_ID, star.starId)
        }
        db.insert(StarExploredHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllStars(): List<Star> {
        val db = readableDatabase
        val starList = mutableListOf<Star>()
        val query = "SELECT * FROM stars"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            starList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return starList
    }

    fun getStarById(starId: Int): Star{
        val db = readableDatabase
        val query = "SELECT * FROM stars WHERE id = $starId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()
            val star = getColumns(cursor)
        cursor.close()
        db.close()
        return star

    }

    fun getStarBySector(s: Int): List<Star>{
        val db = readableDatabase
        val starList = mutableListOf<Star>()
        val query = "SELECT * FROM stars WHERE sector = $s"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            starList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return starList
    }

    fun getStarOrigin(): List<Star> {
        val db = readableDatabase
        val starList = mutableListOf<Star>()
        val query = "SELECT * FROM stars WHERE origin > 0"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            starList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return starList
    }

    fun setStarExplored(id: Int){
        val db = writableDatabase
        val values = ContentValues()
        values.put("explore", 1)
        db.update("stars", values, "id=$id", null)
        db.close()
    }

    fun getStarsExploredBySpecie(specieId: Int): List<Star> {
        val db = readableDatabase
        val starList = mutableListOf<Star>()
        val query = "SELECT stars.* FROM stars INNER JOIN star_explored " +
                "ON stars.id = star_explored.star_id " +
                "WHERE star_explored.specie_id = $specieId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            starList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return starList
    }


}