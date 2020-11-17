package com.ilsang.alarmdiary.ChartRank

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ilsang.alarmdiary.R

class RankViewHolder(item: View) : RecyclerView.ViewHolder(item){
    val rankText : TextView = item.findViewById(R.id.rankTxt)
    val rankIconImg : de.hdodenhof.circleimageview.CircleImageView = item.findViewById(R.id.appLogoImg)
    val nameText : TextView = item.findViewById(R.id.nameTxt)
    val countText : TextView = item.findViewById(R.id.countTxt)

    fun bind(data: RankItem){
        rankText.text = data.rank.toString()+"등"
        rankIconImg.setImageIcon(data.img)
        nameText.text = data.name
        countText.text = data.count.toString()+"건"
    }
}