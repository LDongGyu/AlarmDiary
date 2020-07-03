package com.example.alarmdiary

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmdiary.DataBase.NotificationDbHelper
import com.example.alarmdiary.MainCategory.MainCategoryAdapter
import com.example.alarmdiary.MainPushList.MainPushListViewAdapter
import com.example.alarmdiary.MainPushList.PushItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_layout.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout:DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (!permissionGrantred()) {
            val intent = Intent(
                "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            startActivity(intent)
        }

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) // 홈 버튼 활성화
        supportActionBar?.setHomeAsUpIndicator(R.drawable.menu_hamber) // 홈 버튼 이미지
        supportActionBar?.setDisplayShowTitleEnabled(false)

        drawerLayout = findViewById(R.id.drawer_layout)

        var categoryDatas = listOf("All","Messenger","Social","Shopping","Game","Mail","ETC")
        var mainCategoryAdapter: MainCategoryAdapter = MainCategoryAdapter(categoryDatas)
        categoryRecylerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        categoryRecylerView.adapter = mainCategoryAdapter

        var db = NotificationDbHelper(this)

        var timeFormat = SimpleDateFormat("YYYYMMdd HH:mm")
        var date = Date()
        var dateStr = timeFormat.format(date)
        var dateTime = dateStr.split(" ")

        var pushItem = db.getPushData(dateTime[0])
        pushItem.reversed()
        var mainPushListAdapter =  MainPushListViewAdapter(pushItem)
        pushList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        pushList.adapter = mainPushListAdapter

        analysis_txt.setOnClickListener {
            val intent = Intent(this,ChartActivity::class.java)
            startActivity(intent)
            finish()
        }

        var dateFormat = SimpleDateFormat("YYYY-MM-dd")
        dateTxt.text = dateFormat.format(Date())

        calenderImg.setOnClickListener {
            var c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var mon = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)

            var dateSetListenr = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->

                var cal = Calendar.getInstance()
                cal.set(Calendar.DAY_OF_YEAR,i)
                cal.set(Calendar.DAY_OF_MONTH,i2+1)
                cal.set(Calendar.DATE,i3)

                var date = dateFormat.format(cal.time)
                dateTxt.text = date
                var newDate = timeFormat.format(cal.time)
                var newAlarm = db.getPushData(newDate.split(" ")[0])
                newAlarm.reversed()
                mainPushListAdapter.datas = newAlarm
                mainPushListAdapter.notifyDataSetChanged()
            }

            var builder = DatePickerDialog(this,R.style.DialogTheme, dateSetListenr, year, mon, day)
            builder.show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_item,menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                drawerLayout.openDrawer(GravityCompat.START)
            }
            R.id.etc->{
                Toast.makeText(this,"기타 눌림",Toast.LENGTH_SHORT).show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun permissionGrantred(): Boolean {
        val sets = NotificationManagerCompat.getEnabledListenerPackages(this)
        return sets != null && sets.contains(packageName)
    }
}
