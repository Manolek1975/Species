package com.delek.species.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SpeciesHelper {

    companion object{
        const val TABLE_NAME: String = "species"
        const val COLUMN_ID: String = "id"
        const val COLUMN_NAME: String = "name"
        const val COLUMN_DESC: String = "description"
        const val COLUMN_IMAGE: String = "image"
        const val COLUMN_SKILL: String = "skill"
        const val COLUMN_TYPE: String = "type"
        const val COLUMN_STAR: String = "star"

        val SQL_CREATE_ENTRIES = buildString {
            append("CREATE TABLE $TABLE_NAME (")
            append("$COLUMN_ID INTEGER PRIMARY_KEY,")
            append("$COLUMN_NAME TEXT,")
            append("$COLUMN_DESC TEXT,")
            append("$COLUMN_IMAGE TEXT,")
            append("$COLUMN_SKILL TEXT,")
            append("$COLUMN_TYPE INTEGER,")
            append("$COLUMN_STAR TEXT)")
        }

        val SQL_DELETE_ENTRIES: String = "DROP TABLE IF EXISTS $TABLE_NAME"
    }






}