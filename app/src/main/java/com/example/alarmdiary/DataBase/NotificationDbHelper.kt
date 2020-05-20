package com.example.alarmdiary.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Parcel
import android.os.Parcelable
import android.provider.BaseColumns

class NotificationDbHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Notification.db"

        const val TABLE_NAME = "notification"
        const val COLUMN_NAME_FROM = "Who"
        const val COLUMN_NAME_CONTEXT = "CONTEXT"
        const val COLUMN_NAME_TIME = "TIME"
        const val COLUMN_NAME_APP = "APP"
        const val COLUMN_NAME_ICON = "test"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val SQL_CREATE_ENTRIES = "CREATE TABLE ${TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${COLUMN_NAME_FROM} TEXT," +
                "${COLUMN_NAME_CONTEXT} TEXT," +
                "${COLUMN_NAME_TIME} TEXT," +
                "${COLUMN_NAME_APP} TEXT," +
                "${COLUMN_NAME_ICON} TEXT)"
        p0!!.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"
        p0!!.execSQL(SQL_DELETE_ENTRIES)
        onCreate(p0)
    }
}





