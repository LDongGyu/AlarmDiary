package com.example.alarmdiary

import android.app.Notification
import android.content.ContentValues
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.example.alarmdiary.DataBase.NotificationDbHelper

class NotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        val notification = sbn?.notification
        val extras = sbn?.notification?.extras
        val title = extras?.getString(Notification.EXTRA_TITLE)
        val text = extras?.getCharSequence(Notification.EXTRA_TEXT)
        val subText = extras?.getCharSequence(Notification.EXTRA_SUB_TEXT)
        val smallIcon = notification?.smallIcon
        val largeIcon = notification?.getLargeIcon()


        val dbHelper = NotificationDbHelper(applicationContext)
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(NotificationDbHelper.COLUMN_NAME_FROM,title)
            put(NotificationDbHelper.COLUMN_NAME_CONTEXT,text.toString())
            put(NotificationDbHelper.COLUMN_NAME_TIME,sbn?.postTime)
            put(NotificationDbHelper.COLUMN_NAME_APP,sbn?.packageName)
            put(NotificationDbHelper.COLUMN_NAME_ICON,smallIcon.toString())
        }

        db?.insert(NotificationDbHelper.TABLE_NAME,null,values)

        Log.d("PushLog","onNotificationPosted ~ " +
                " packageName: " + sbn?.packageName +
                " id: " + sbn?.id +
                " postTime: " + sbn?.postTime +
                " title: " + title +
                " text : " + text +
                " subText: " + subText)


    }
}
