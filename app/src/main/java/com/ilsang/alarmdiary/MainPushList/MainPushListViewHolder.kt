package com.ilsang.alarmdiary.MainPushList

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ilsang.alarmdiary.R

class MainPushListViewHolder(item: View) : RecyclerView.ViewHolder(item){
    val pushListIconImg : de.hdodenhof.circleimageview.CircleImageView = item.findViewById(R.id.appLogoImg)
    val pushNameTxt : TextView = item.findViewById(R.id.nameTxt)
    val pushContentTxt : TextView = item.findViewById(R.id.contentTxt)
    val pushTimeTxt : TextView = item.findViewById(R.id.timeTxt)
    var context = item.context

    fun bind(data: PushItem){
        pushListIconImg.setImageIcon(data.img)
        pushNameTxt.text = data.name
        pushContentTxt.text = data.content
        pushTimeTxt.text = data.time
    }
}