package com.example.alarmdiary

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmdiary.ChartRank.RankItem
import com.example.alarmdiary.ChartRank.RankViewAdapter
import com.example.alarmdiary.DataBase.NotificationDbHelper
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.activity_chart.*
import kotlinx.android.synthetic.main.activity_chart.dateTxt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_layout.*
import kotlinx.android.synthetic.main.toolbar_layout.*
import java.text.SimpleDateFormat
import java.util.*

class ChartActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout
    var timeFormat = SimpleDateFormat("YYYYMMdd HH:mm")
    var dateFormat = SimpleDateFormat("YYYY-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chart)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_hamber) // 홈 버튼 이미지
        supportActionBar?.setDisplayShowTitleEnabled(false)

        toolbar_title.text = "통계"

        drawerLayout = findViewById(R.id.drawer_layout)

        var db = NotificationDbHelper(this)

        var date = Date()
        var dateStr = timeFormat.format(date)
        var rankItem = db.getRankData(dateStr.split(" ")[0])

        var rankAdapter = RankViewAdapter(rankItem)
        rankList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rankList.adapter = rankAdapter

        var data = mutableListOf<BarEntry>()
        var label = mutableListOf<String>()
        for(i in 0..rankItem.size-1){
            var temp = BarEntry(rankItem[i].count.toFloat(),i)
            data.add(i,temp)
            label.add(i,rankItem[i].name)
        }

        var dataSet = BarDataSet(data,"Label")
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        var barData = BarData(label,dataSet)
        barChart.data = barData
        barChart.invalidate()

        main_txt.setOnClickListener {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        dateTxt.text = dateFormat.format(Date())
        
        val calender = findViewById(R.id.calenderImg) as ImageView
        calender.setOnClickListener {
            var c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var mon = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)

            var dateSetListenr = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

                var cal = Calendar.getInstance()
                cal.set(Calendar.DAY_OF_YEAR,i)
                cal.set(Calendar.DAY_OF_MONTH,i2+1)
                cal.set(Calendar.DATE,i3)

                dateTxt.text = dateFormat.format(cal.time)
                var newDate = timeFormat.format(cal.time)
                var newRank = db.getRankData(newDate.split(" ")[0])
                rankAdapter.datas = newRank
                rankAdapter.notifyDataSetChanged()

                var data = mutableListOf<BarEntry>()
                var label = mutableListOf<String>()
                for(i in 0..newRank.size-1){
                    var temp = BarEntry(newRank[i].count.toFloat(),i)
                    data.add(i,temp)
                    label.add(i,newRank[i].name)
                }

                var dataSet = BarDataSet(data,"Label")
                dataSet.setColors(ColorTemplate.COLORFUL_COLORS)
                var barData = BarData(label,dataSet)
                barChart.data = barData
                barChart.invalidate()
            }

            var builder = DatePickerDialog(this, R.style.DialogTheme, dateSetListenr, year, mon, day)
            builder.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}