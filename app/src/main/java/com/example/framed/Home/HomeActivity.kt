package com.example.framed.Home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.example.framed.R
import com.example.framed.Utils.BottomNavigationViewHelper
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.framed.Utils.Base
import com.example.framed.Utils.Base.Companion.CHANNEL_1_ID
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException


class HomeActivity : AppCompatActivity() {

    private val ACTIVITY_NUM = 0
    lateinit var viewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //createNotificationChannels()

        Log.d("HomeActivity","started")
        setupBottomNavigationView()
        setupViewPager()

        //this.deleteDatabase("game");
        //recyclerViewHome.layoutManager = LinearLayoutManager(this)
    }

    /*private fun createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel1 = NotificationChannel(
                Base.CHANNEL_1_ID,
                "Channel 1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "testing Notification"

            val manager: NotificationManager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel1)

        }
    }*/

    //Responsible for adding 2 tabs, playList and upcoming
    private fun setupViewPager(){
        val adapter = SectionsPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(UpcomingFragment())
        viewPager = findViewById(R.id.container) as ViewPager
        viewPager.setAdapter(adapter)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)?.setText("RELEASED")
        tabLayout.getTabAt(1)?.setText("UPCOMING")

    }
    private fun setupBottomNavigationView(){

        /*val buttonNavigationViewEx: BottomNavigationViewEx =
            findViewById(R.id.bottomNavViewBar)*/

        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.bottomNavViewBar)

        val helperClass = BottomNavigationViewHelper()
        helperClass.enableNavigation(this, bottomNavigationView)

        val menu: Menu = bottomNavigationView.getMenu()
        val menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.setChecked(true)

    }


    override fun onResume() {
        super.onResume()
        this.overridePendingTransition(0,0)
        setupBottomNavigationView()
        /*val adapter = SectionsPagerAdapter(supportFragmentManager)
        adapter.addFragment(HomeFragment())
        adapter.addFragment(UpcomingFragment())
        viewPager = findViewById(R.id.container) as ViewPager
        viewPager.setAdapter(adapter)

        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        tabLayout.setupWithViewPager(viewPager)

        tabLayout.getTabAt(0)?.setText("RELEASED")
        tabLayout.getTabAt(1)?.setText("UPCOMING")*/

    }




}
