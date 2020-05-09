package com.example.alarmdiary

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

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

        Log.d("PushLog","onNotificationPosted ~ " +
                " packageName: " + sbn?.packageName +
                " id: " + sbn?.id +
                " postTime: " + sbn?.postTime +
                " title: " + title +
                " text : " + text +
                " subText: " + subText)
    }
}
