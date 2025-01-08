package com.delek.species.database.helper

import android.content.Context
import com.delek.species.database.dao.PlanetDAO
import com.delek.species.database.dao.StarDAO
import com.delek.species.database.model.Planet

class PlanetHelper {
    companion object{
        const val TABLE_NAME: String = "planets"
        const val COLUMN_ID: String = "id"
        const val COLUMN_STAR: String = "star"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_POSITION: String = "position"
        const val COLUMN_SIZE: String = "size"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_OWNER: String = "owner"
        const val COLUMN_FOOD: String = "food"
        const val COLUMN_PRODUCTION: String = "production"
        const val COLUMN_RESEARCH: String = "research"
        const val COLUMN_DEFENSE: String = "defense"
        const val COLUMN_POPULATION: String = "population"


        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,")
            append("$COLUMN_STAR INTEGER,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_POSITION INTEGER,")
            append("$COLUMN_SIZE INTEGER,")
            append("$COLUMN_TYPE INTEGER,")
            append("$COLUMN_OWNER INTEGER,")
            append("$COLUMN_FOOD INTEGER,")
            append("$COLUMN_PRODUCTION INTEGER,")
            append("$COLUMN_RESEARCH INTEGER,")
            append("$COLUMN_DEFENSE INTEGER,")
            append("$COLUMN_POPULATION INTEGER)")
        }

        const val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"

        fun loadPlanets(context: Context) {
            val star = StarDAO(context).getAllStars()
            var rnd: Int
            var rndTypes: Int
            for (i in star){
                rnd = (1..8).random()
                if (i.owner !=0) rnd = 3 // Limit origin star to 3 planets
                for (j in 1..rnd){
                    println(j)
                    var owner = 0; var food = 0; var prod = 0; var pop = 0
                    rndTypes = (1..12).random()
                    if (i.owner !=0) rndTypes = j // Limit origin type to 1..3
                    if (j == 2) {
                        owner = i.owner; food = 1; prod = 1; pop = 20 // Set values to Origin planet
                    }
                    val image = getPlanetImage(rndTypes)
                    val planet = Planet(0, i.id, i.name +" "+ getSub(j), image, j, setSize(j), rndTypes,
                        owner, food, prod,0,0, pop)
                    PlanetDAO(context).insertPlanets(planet)
                }
            }
        }

        private fun getPlanetImage(image: Int): String {
            return when (image) {
                1 -> return "planet1_arido"
                2 -> return "planet2_primordial"
                3 -> return "planet3_agricola"
                4 -> return "planet4_eden"
                5 -> return "planet5_mineral"
                6 -> return "planet6_supermineral"
                7 -> return "planet7_experimental"
                8 -> return "planet8_peculiar"
                9 -> return "planet9_especial"
                10 -> return "planet10_singular"
                11 -> return "planet11_cornucopia"
                12 -> return "planet12_helado"

                else -> ({}).toString()
            }
        }

        private fun setSize(j: Int): Int {
            return when (j) {
                1 -> 1
                in 2..4 -> 2
                in 5..7 -> 3
                8 -> 1
                else -> 0
            }
        }

        private fun getSub(id: Int): String {
            val sub = listOf("b", "c", "d", "e", "f", "g", "h", "k")
            return sub[id-1]
        }
    }


}