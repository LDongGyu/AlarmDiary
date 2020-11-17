package com.ilsang.alarmdiary.DataBase

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.provider.BaseColumns
import com.ilsang.alarmdiary.ChartRank.RankItem
import com.ilsang.alarmdiary.MainPushList.PushItem
import java.io.ByteArrayInputStream

class NotificationDbHelper (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Notification.db"

        const val TABLE_NAME = "notification"
        const val COLUMN_NAME_FROM = "WHO"
        const val COLUMN_NAME_CONTEXT = "CONTEXT"
        const val COLUMN_NAME_DATE = "DATE"
        const val COLUMN_NAME_TIME = "TIME"
        const val COLUMN_NAME_APP = "APP"
        const val COLUMN_NAME_ICON = "ICON"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val SQL_CREATE_ENTRIES = "CREATE TABLE ${TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                "${COLUMN_NAME_FROM} TEXT," +
                "${COLUMN_NAME_CONTEXT} TEXT," +
                "${COLUMN_NAME_DATE} TEXT," +
                "${COLUMN_NAME_TIME} TEXT," +
                "${COLUMN_NAME_APP} TEXT," +
                "${COLUMN_NAME_ICON} BLOB)"
        p0!!.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${TABLE_NAME}"
        p0!!.execSQL(SQL_DELETE_ENTRIES)
        onCreate(p0)
    }

    public fun getPushData(date: String): List<PushItem> {
        val notiList = ArrayList<PushItem>()
        val selectQueryHandler = "SELECT * FROM ${TABLE_NAME} WHERE ${COLUMN_NAME_DATE} = ${date}"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQueryHandler,null)

        if(cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                val pushItem = PushItem()

                pushItem.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_FROM)) ?: "test"
                pushItem.content = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_CONTEXT)) ?: "테스트 중"
                pushItem.date = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_DATE)) ?: "20200610"
                pushItem.time = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_TIME)) ?: "00:00"
                var bitmap = cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_ICON))
                var inputStream = ByteArrayInputStream(bitmap)
                var bitmapImg = BitmapFactory.decodeStream(inputStream)
                pushItem.img = Icon.createWithBitmap(bitmapImg)
                pushItem.appName = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_APP)) ?: "AlarmDiary"
                notiList.add(pushItem)
                cursor.moveToNext()
            }
            cursor.close()
        }
        db.close()
        return notiList
    }

    fun getRankData(date: String): List<RankItem>{
        val rankList = ArrayList<RankItem>()
        val selectQueryHandler = "SELECT $COLUMN_NAME_FROM, COUNT($COLUMN_NAME_CONTEXT) as Count, $COLUMN_NAME_ICON FROM $TABLE_NAME WHERE ${COLUMN_NAME_DATE} = ${date} GROUP BY $COLUMN_NAME_FROM ORDER BY COUNT($COLUMN_NAME_CONTEXT) DESC"
        val db = this.writableDatabase
        val cursor = db.rawQuery(selectQueryHandler,null)

        if(cursor.moveToFirst()) {
            var rank = 1
            while (!cursor.isAfterLast()) {
                val rankItem = RankItem()

                rankItem.name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME_FROM)) ?: "test"
                rankItem.count = cursor.getInt(cursor.getColumnIndex("Count")) ?: 0
                var bitmap = cursor.getBlob(cursor.getColumnIndex(COLUMN_NAME_ICON))
                var inputStream = ByteArrayInputStream(bitmap)
                var bitmapImg = BitmapFactory.decodeStream(inputStream)
                rankItem.img = Icon.createWithBitmap(bitmapImg)
                rankItem.rank = rank
                rank++
                rankList.add(rankItem)
                cursor.moveToNext()
                if(rank == 6){
                    break
                }
            }
            cursor.close()
        }
        db.close()
        return rankList
    }
}





