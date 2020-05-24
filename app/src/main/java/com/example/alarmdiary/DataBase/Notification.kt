package com.example.alarmdiary.DataBase

data class Notification(
    var title: String = "title",
    var text: String = "context",
    var postTime: String = "00:00",
    var packageName: String = "AlarmDiary",
    var smallIcon: String = "ICON"
)