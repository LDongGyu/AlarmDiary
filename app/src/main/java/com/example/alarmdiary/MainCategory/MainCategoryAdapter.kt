package com.example.alarmdiary.MainCategory

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmdiary.R

class MainCategoryAdapter(var datas: List<String>) : RecyclerView.Adapter<MainCategoryViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainCategoryViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.category_item,parent,false)
        return MainCategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    override fun onBindViewHolder(holder: MainCategoryViewHolder, position: Int) {
        holder.bind(datas[position])
    }

}