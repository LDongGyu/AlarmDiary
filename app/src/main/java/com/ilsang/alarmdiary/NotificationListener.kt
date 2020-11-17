package com.ilsang.alarmdiary

import android.app.Notification
import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.drawable.*
import android.os.Build
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import com.ilsang.alarmdiary.DataBase.NotificationDbHelper
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class NotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        super.onNotificationPosted(sbn)

        val notification = sbn?.notification
        val extras = sbn?.notification?.extras
        val title = extras?.getString(Notification.EXTRA_TITLE)
        val text = extras?.getCharSequence(Notification.EXTRA_TEXT)

        var icon = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P && notification != null) {
            if(notification?.getLargeIcon() != null){
                notification?.getLargeIcon().loadDrawable(applicationContext)
            }
            else if(notification?.smallIcon != null){
                notification?.smallIcon.loadDrawable(applicationContext)
            }
            else{
                Icon.createWithResource(applicationContext,R.drawable.logo_color).loadDrawable(applicationContext)
            }
        } else {
            Icon.createWithResource(applicationContext,R.drawable.logo_color).loadDrawable(applicationContext)
        }

        var timeStemp = sbn?.postTime ?: 0

        var timeFormat = SimpleDateFormat("YYYYMMdd HH:mm")
        var date = Date(timeStemp)
        var dateStr = timeFormat.format(date)
        var dateTime = dateStr.split(" ")

        val dbHelper = NotificationDbHelper(applicationContext)
        val db = dbHelper.writableDatabase

//        Log.d("PushLog","onNotificationPosted ~ " +
//                " title: " + title +
//                " text : " + text +
//                " date : " + dateTime[0] + " " + dateTime[1] +
//                " packageName : " + sbn?.packageName +
//                " icon : " + icon)

        if(icon !is VectorDrawable){
            var bitmapDrawable = icon as BitmapDrawable
            var bitmap = bitmapDrawable.bitmap
            var stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
            var bitmapdata = stream.toByteArray()

            val values = ContentValues().apply {
                put(NotificationDbHelper.COLUMN_NAME_FROM,title)
                put(NotificationDbHelper.COLUMN_NAME_CONTEXT,text.toString())
                put(NotificationDbHelper.COLUMN_NAME_DATE,dateTime[0])
                put(NotificationDbHelper.COLUMN_NAME_TIME,dateTime[1])
                put(NotificationDbHelper.COLUMN_NAME_APP,sbn?.packageName)
                put(NotificationDbHelper.COLUMN_NAME_ICON,bitmapdata)
            }
            if(!sbn?.packageName.equals("com.android.systemui") && notification?.getLargeIcon() != null && notification?.smallIcon != null ){
                db?.insert(NotificationDbHelper.TABLE_NAME,null,values)
            }
        }


    }
}
