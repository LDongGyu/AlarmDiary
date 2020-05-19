package com.example.alarmdiary

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alarmdiary.MainCategory.MainCategoryAdapter
import com.example.alarmdiary.MainPushList.MainPushListViewAdapter
import com.example.alarmdiary.MainPushList.PushItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_layout.*
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

        var temp1 = PushItem(R.drawable.kakao,"이동규","테스트 중 입니다.","12:52")
        var temp2 = PushItem(R.drawable.kakao,"유인근", "카톡하고 있습니다.","12:31")
        var temp3 = PushItem(R.drawable.insta,"죠르디","어서 출시해주세요!","12:22")
        var temp4 = PushItem(R.drawable.kakao,"박승완","멋있다 동규야","11:55")
        var temp5 = PushItem(R.drawable.insta,"이현지","동규야 보고싶어","10:48")
        var temp6 = PushItem(R.drawable.kakao,"이의진","우리 언제 만날까? ㅎㅎ","10:37")
        var temp7 = PushItem(R.drawable.insta,"이소혜","자니...?","03:21")


        val pushDatas = listOf(temp1,temp2,temp3,temp4,temp5,temp6,temp7)
        var mainPushListAdapter =  MainPushListViewAdapter(pushDatas)
        pushList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        pushList.adapter = mainPushListAdapter

        analysis_txt.setOnClickListener {
            val intent = Intent(this,ChartActivity::class.java)
            startActivity(intent)
            finish()
        }

        calenderImg.setOnClickListener {
            var c = Calendar.getInstance()
            var year = c.get(Calendar.YEAR)
            var mon = c.get(Calendar.MONTH)
            var day = c.get(Calendar.DAY_OF_MONTH)

            var dateSetListenr = DatePickerDialog.OnDateSetListener { datePicker, i, i2, i3 ->
                dateTxt.text = "${i}-${i2+1}-${i3}"
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
