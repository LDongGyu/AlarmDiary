package com.example.alarmdiary.MainPushList

import android.graphics.drawable.Icon

data class PushItem (
    var img: Icon = Icon.createWithFilePath(""),
    var name: String = "이동규",
    var content: String = "테스트",
    var date: String = "20200610",
    var time: String = "00:00",
    var appName : String = "AlarmDiary"
)