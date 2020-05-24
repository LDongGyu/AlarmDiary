package com.example.alarmdiary.MainPushList

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmdiary.R
import kotlinx.android.synthetic.main.push_list_layout.view.*

class MainPushListViewHolder(item: View) : RecyclerView.ViewHolder(item){
    val pushListIconImg : de.hdodenhof.circleimageview.CircleImageView = item.findViewById(R.id.appLogoImg)
    val pushNameTxt : TextView = item.findViewById(R.id.nameTxt)
    val pushContentTxt : TextView = item.findViewById(R.id.contentTxt)
    val pushTimeTxt : TextView = item.findViewById(R.id.timeTxt)

    fun bind(data: PushItem){
        pushListIconImg.setImageResource(data.img)
        pushNameTxt.text = data.name
        pushContentTxt.text = data.content
        pushTimeTxt.text = data.time
    }
}