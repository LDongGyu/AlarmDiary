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
import java.util.*

class ChartActivity : AppCompatActivity() {

    lateinit var drawerLayout: DrawerLayout

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
        var rankItem = db.getRankData()

        var rankAdapter = RankViewAdapter(rankItem)
        rankList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rankList.adapter = rankAdapter

        var temp1 = BarEntry(rankItem[0].count as Float,0)
        var temp2 = BarEntry(rankItem[1].count as Float,1)
        var temp3 = BarEntry(rankItem[2].count as Float,2)
        var temp4 = BarEntry(rankItem[3].count as Float,3)
        var temp5 = BarEntry(rankItem[4].count as Float,4)

        var data = listOf<BarEntry>(temp1,temp2,temp3,temp4,temp5)

        var label = listOf(rankItem[0].name,rankItem[1].name,rankItem[2].name,rankItem[3].name,rankItem[4].name)
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

        val calender = findViewById(R.id.calenderImg) as ImageView
        calender.setOnClickListener {
            var c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var mon = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)

            var dateSetListenr = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                dateTxt.text = "${i}-${i2+1}-${i3}"
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