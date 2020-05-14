package com.example.alarmdiary.MainPushList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmdiary.R

class MainPushListViewAdapter(var datas: List<PushItem>) : RecyclerView.Adapter<MainPushListViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPushListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.push_list_layout,parent,false)
        return MainPushListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MainPushListViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}