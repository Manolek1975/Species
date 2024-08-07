package com.delek.species.database.dao


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Star
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.StarHelper


class StarDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {


    fun getAllStars(): List<Star>{
        val starList = mutableListOf<Star>()
        val db = readableDatabase
        val query = "SELECT * FROM stars"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_IMAGE))
            val sector = cursor.getString(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_SECTOR))
            val jumps = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_JUMPS))
            val x = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_X))
            val y = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_Y))
            val type = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_TYPE))
            val explore = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_EXPLORE))

            val star = Star(id, name, image, sector, jumps, x, y, type, explore)
            starList.add(star)
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

        val id = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_NAME))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_IMAGE))
        val sector = cursor.getString(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_SECTOR))
        val jumps = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_JUMPS))
        val x = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_X))
        val y = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_Y))
        val type = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_TYPE))
        val explore = cursor.getInt(cursor.getColumnIndexOrThrow(StarHelper.COLUMN_EXPLORE))

        return Star(id, name, image, sector, jumps, x, y, type, explore)
    }

    fun getStarOrigin(id: Int): Boolean {
        val db = readableDatabase
        val result: Boolean
        val query = "SELECT * FROM species WHERE species.origin = $id"
        val cursor = db.rawQuery(query, null)
        result = cursor.count >= 1
        cursor.close()
        db.close()
        return result
    }

    fun setStarExplored(id: Int){
        val db = readableDatabase
        val values = ContentValues()
        values.put("explore", 1)
        db.update("stars", values, "id=$id", null)
        db.close()
    }


    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}