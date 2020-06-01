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

        var temp1 = BarEntry(20F,0)
        var temp2 = BarEntry(12F,1)
        var temp3 = BarEntry(5F,2)
        var temp4 = BarEntry(18F,3)
        var temp5 = BarEntry(10F,4)

        var data = listOf<BarEntry>(temp1,temp2,temp3,temp4,temp5)

        var label = listOf("이동규","죠르디","라이언","테스트","안드로")
        var dataSet = BarDataSet(data,"Label")
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS)
        var barData = BarData(label,dataSet)
        barChart.data = barData
        barChart.invalidate()

        val rankTemp1 = RankItem(1,R.drawable.kakao,"이동규",20)
        val rankTemp2 = RankItem(2,R.drawable.insta,"죠르디",19)
        val rankTemp3 = RankItem(3,R.drawable.insta,"라이언",18)
        val rankTemp4 = RankItem(4,R.drawable.kakao,"테스트",17)
        val rankTemp5 = RankItem(5,R.drawable.kakao,"안드로",6)

        val rankDatas = listOf(rankTemp1,rankTemp2,rankTemp3,rankTemp4,rankTemp5)
        var rankAdapter = RankViewAdapter(rankDatas)
        rankList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rankList.adapter = rankAdapter

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