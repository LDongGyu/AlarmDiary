package com.ilsang.alarmdiary.ChartRank

import android.graphics.drawable.Icon

data class RankItem(
    var rank : Int = 5,
    var img : Icon = Icon.createWithFilePath(""),
    var name : String = "테스트",
    var count : Int = 0
)