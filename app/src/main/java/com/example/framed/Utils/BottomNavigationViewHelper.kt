package com.example.framed.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
//import androidx.test.espresso.matcher.ViewMatchers.isChecked
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.framed.Home.HomeActivity
import com.example.framed.Profile.ProfileActivity
import com.example.framed.R
import com.example.framed.Search.DiscoverActivity



class BottomNavigationViewHelper{

    fun setupBottomNavigationViewEx(bottomNavigationViewEx: BottomNavigationViewEx){

        bottomNavigationViewEx.enableAnimation(false)
        bottomNavigationViewEx.enableItemShiftingMode(false)
        bottomNavigationViewEx.enableShiftingMode(false)

    }

    fun enableNavigation(context: Context, view: BottomNavigationView){
        view.setOnNavigationItemSelectedListener(
            BottomNavigationView.OnNavigationItemSelectedListener{ item ->

            when (item.itemId) {
                com.example.framed.R.id.ic_games -> {
                    val intent1 = Intent(context, HomeActivity::class.java) //ACTIVITY_NUM = 0
                    context.startActivity(intent1)
                    (context as Activity).overridePendingTransition(0,0)
                }
                com.example.framed.R.id.ic_search -> {
                    val intent2 = Intent(context, DiscoverActivity::class.java) //ACTIVITY_NUM = 2
                    intent2.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
                    context.startActivity(intent2)
                }
                com.example.framed.R.id.ic_profile -> {
                    val intent3 = Intent(context, ProfileActivity::class.java) //ACTIVITY_NUM = 3
                    context.startActivity(intent3)
                    (context as Activity).overridePendingTransition(0,0)
                }

            }

            true
        })




    }



}