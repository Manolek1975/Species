package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.database.dataclass.Ship
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.ShipHelper

class ShipDAO(context: Context) : SQLiteOpenHelper(context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    fun insertShips(ship: Ship) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(ShipHelper.COLUMN_NAME, ship.name)
            put(ShipHelper.COLUMN_IMAGE, ship.image)
            put(ShipHelper.COLUMN_SPECIE_ID, ship.specieId)
            put(ShipHelper.COLUMN_ORBIT, ship.orbit)
            put(ShipHelper.COLUMN_ROUTE, ship.route)
            put(ShipHelper.COLUMN_DAYS, ship.days)
        }
        db.insert(ShipHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllShips(): List<Ship> {
        val shipList = mutableListOf<Ship>()
        val db = readableDatabase
        val query = "SELECT * FROM ships"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_IMAGE))
            val specieId = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_SPECIE_ID))
            val orbit = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ORBIT))
            val route = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ROUTE))
            val days = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_DAYS))

            val ship = Ship(id, name, image, specieId, orbit, route, days)
            shipList.add(ship)
        }
        cursor.close()
        db.close()
        return shipList
    }

    fun getShipBySpecie(specieID: Int?): Ship {
        var ship = Ship()
        val db = readableDatabase
        val query = "SELECT * FROM ships WHERE specie_id = $specieID"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_IMAGE))
            val specieId = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_SPECIE_ID))
            val orbit = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ORBIT))
            val route = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ROUTE))
            val days = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_DAYS))

            ship = Ship(id, name, image, specieId, orbit, route, days)
        }
        cursor.close()
        db.close()
        return ship
    }

    fun updateOrbitShip(planetId: Int, specie: Int) {
        val db = readableDatabase
        val values = ContentValues()
        values.put("orbit", planetId)
        db.update("ships", values, "id = $specie", null)
    }

    fun getShipsByPlanet(planetId: Int): List<Ship> {
        val db = readableDatabase
        val shipList = mutableListOf<Ship>()
        val query = "SELECT * FROM ships WHERE orbit = $planetId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_NAME))
            val image = cursor.getString(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_IMAGE))
            val specieId = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_SPECIE_ID))
            val orbit = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ORBIT))
            val route = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ROUTE))
            val days = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_DAYS))
            val ship = Ship(id, name, image, specieId, orbit, route, days)
            shipList.add(ship)
            }
        cursor.close()
        db.close()
        return shipList
    }
}

