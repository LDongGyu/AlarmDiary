package com.example.alarmdiary.MainCategory

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.alarmdiary.R
import kotlinx.android.synthetic.main.category_item.view.*

class MainCategoryViewHolder(item: View) : RecyclerView.ViewHolder(item){
    val categoryString : TextView = item.findViewById(R.id.categoryTxt)

    fun bind(data: String) {
        categoryString.text = data
    }
}