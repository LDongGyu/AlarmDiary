package com.example.alarmdiary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var drawerLayout:DrawerLayout
    lateinit var drawerView: View

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
//        drawerView = findViewById(R.id.drawer)
//        val actionBarDrawerToggle:ActionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar,0,1)
//        drawerLayout.addDrawerListener(actionBarDrawerToggle)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                Toast.makeText(applicationContext,"Test",Toast.LENGTH_SHORT).show()
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun permissionGrantred(): Boolean {
        val sets = NotificationManagerCompat.getEnabledListenerPackages(this)
        return sets != null && sets.contains(packageName)
    }
}
