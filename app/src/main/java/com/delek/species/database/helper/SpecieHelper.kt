package com.delek.species.database.helper

import android.content.Context
import com.delek.species.R
import com.delek.species.dao.SpecieDAO
import com.delek.species.database.model.Specie

class SpecieHelper {

    companion object{
        const val TABLE_NAME: String = "species"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_DESC: String = "description"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_SHIP: String = "ship"
        const val COLUMN_IMG_SHIP: String = "img_ship"
        const val COLUMN_SKILL: String = "skill"
        const val COLUMN_STAR: String = "star"
        const val COLUMN_COLOR: String = "color"
        const val COLUMN_ORIGIN: String = "origin"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_DESC TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_SKILL TEXT,")
            append("$COLUMN_SHIP TEXT,")
            append("$COLUMN_IMG_SHIP TEXT,")
            append("$COLUMN_STAR INTEGER,")
            append("$COLUMN_COLOR TEXT,")
            append("$COLUMN_ORIGIN INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        // Load resources from xml files to database
        fun loadSpecies(context: Context){
            val res = context.resources
            val name = res.getStringArray(R.array.name_species)
            val desc = res.getStringArray(R.array.description_species)
            val image = res.getStringArray(R.array.image_species)
            val ship = res.getStringArray(R.array.ship_species)
            val imgShip = res.getStringArray(R.array.image_ship_species)
            val star = res.getStringArray(R.array.origin_species)
            val color = res.getStringArray(R.array.color_species)
            val origin = res.getStringArray(R.array.origin_species)

            for (i in name.indices){
                val specie = Specie(0, name[i], desc[i], image[i], ship[i], imgShip[i],
                    "", star[i].toInt(), color[i], origin[i].toInt())
                SpecieDAO(context).insertSpecies(specie)
            }
        }
    }

}