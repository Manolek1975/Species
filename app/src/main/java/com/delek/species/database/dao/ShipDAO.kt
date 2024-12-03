package com.delek.species.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.delek.species.R
import com.delek.species.database.model.Ship
import com.delek.species.database.helper.DBHelper
import com.delek.species.database.helper.ShipHelper


class ShipDAO(context: Context) : SQLiteOpenHelper(
    context,
    DBHelper.DATABASE_NAME, null,
    DBHelper.DATABASE_VERSION
) {
    override fun onCreate(p0: SQLiteDatabase?) {}
    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {}

    private fun getColumns(cursor: Cursor): Ship {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ID))
        val name = cursor.getString(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_NAME))
        val image = cursor.getString(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_IMAGE))
        val specieId = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_SPECIE_ID))
        val orbit = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ORBIT))
        val route = cursor.getInt(cursor.getColumnIndexOrThrow(ShipHelper.COLUMN_ROUTE))

        val ship = Ship(id, name, image, specieId, orbit, route)
        return ship
    }

    fun insertShips(ship: Ship) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(ShipHelper.COLUMN_NAME, ship.name)
            put(ShipHelper.COLUMN_IMAGE, ship.image)
            put(ShipHelper.COLUMN_SPECIE_ID, ship.specieId)
            put(ShipHelper.COLUMN_ORBIT, ship.orbit)
            put(ShipHelper.COLUMN_ROUTE, ship.route)
        }
        db.insert(ShipHelper.TABLE_NAME, null, values)
        db.close()
    }

    fun getAllShips(): List<Ship> {
        val db = readableDatabase
        val shipList = mutableListOf<Ship>()
        val query = "SELECT * FROM ships"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            shipList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return shipList
    }

    fun getShipsBySpecie(specieID: Int): List<Ship> {
        val db = readableDatabase
        val shipList = mutableListOf<Ship>()
        val query = "SELECT * FROM ships WHERE specie_id = $specieID"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            shipList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return shipList
    }

    fun getShipsByPlanet(planetId: Int): List<Ship> {
        val db = readableDatabase
        val shipList = mutableListOf<Ship>()
        val query = "SELECT * FROM ships WHERE orbit = $planetId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            shipList.add(getColumns(cursor))
        }
        cursor.close()
        db.close()
        return shipList
    }

    fun getShipById(shipId: Int): Ship {
        val db = readableDatabase
        var ship = Ship()
        val query = "SELECT * FROM ships WHERE id = $shipId"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            ship = getColumns(cursor)
        }
        cursor.close()
        db.close()
        return ship
    }

    fun updateRouteShip(shipId: Int, planetId: Int) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("route", planetId)
        values.put("orbit", 0)
        db.update("ships", values, "id = $shipId", null)
        db.close()
    }

    fun updateOrbitShip(planetId: Int, specie: Int) {
        val db = writableDatabase
        val values = ContentValues()
        values.put("orbit", planetId)
        values.put("route", 0)
        db.update("ships", values, "id = $specie", null)
        db.close()
    }

    fun getLastShip(): Int {
        val db = readableDatabase
        val query = "SELECT MAX(rowid) FROM ships"
        val cursor = db.rawQuery(query, null)
        cursor.moveToNext()
            val lastId = cursor.getInt(0)
        cursor.close()
        db.close()
        return lastId



    }


}

