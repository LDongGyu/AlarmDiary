package com.example.alarmdiary.ChartRank

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmdiary.R

class RankViewAdapter(var datas: List<RankItem>) : RecyclerView.Adapter<RankViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.rank_item,parent,false)
        return RankViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: RankViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}