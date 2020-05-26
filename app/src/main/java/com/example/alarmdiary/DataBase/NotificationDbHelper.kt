package com.example.alarmdiary.DataBase

import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Parcel
import android.os.Parcelable
import android.provider.BaseColumns
import com.example.alarmdiary.MainPushList.PushItem
import com.example.alarmdiary.R

class NotificationDbHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Notification.db"

        const val TABLE_NAME = "notification"
        const val COLUMN_NAME_FROM = "Who"
        const val COLUMN_NAME_CONTEXT = "CONTEXT"
        const val COLUMN_NAME_TIME = "TIME"
        const val COLUMN_NAME_APP = "APP"
        const val COLUMN_NAME_ICON = "ICON"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val SQL_CREATE_ENTRIES = "CREATE TABLE ${TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${COLUMN_NAME_FROM} TEXT," +
                "${COLUMN_NAME_CONTEXT} TEXT," +
                "${COLUMN_NAME_TIME} TEXT," +
                "${COLUMN_NAME_APP} TEXT," +
                "${COLUMN_NAME_ICON} INTEGER)"
        p0!!.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"
        p0!!.execSQL(SQL_DELETE_ENTRIES)
        onCreate(p0)
    }

    public fun getAllData(): List<PushItem> {
        val notiList = ArrayList<PushItem>()
        val selectQueryHandler = "SELECT * FROM $TABLE_NAME"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQueryHandler,null)

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                val pushItem = PushItem()

                pushItem.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_FROM)) ?: "test"
                pushItem.content = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CONTEXT)) ?: "테스트 중"
                pushItem.time = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TIME)) ?: "00:00"
                pushItem.img = R.drawable.logo_color
//                pushItem.img = cursor.getInt(cursor.getColumnIndex(COLUMN_NAME_ICON)) ?: R.drawable.logo_color
                pushItem.appName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_APP)) ?: "AlarmDiary"
                notiList.add(pushItem)
                cursor.moveToNext()
            }
            cursor.close()
        }
        db.close()
        return notiList
    }
}





