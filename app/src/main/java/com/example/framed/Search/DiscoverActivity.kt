package com.example.framed.Search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.Toolbar
import androidx.cardview.widget.CardView
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.RecommendedRecyclerViewAdapter
import com.example.framed.R
import com.example.framed.TopRatedRecyclerViewAdapter
import com.example.framed.TrendingRecyclerViewAdapter
import com.example.framed.Utils.BottomNavigationViewHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class DiscoverActivity : AppCompatActivity() {

    private val ACTIVITY_NUM = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        Log.d("DiscoverActivity","started")
        setupBottomNavigationView()


        val rec1: RecyclerView = findViewById(R.id.recyclerViewRecommended)
        val layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rec1.layoutManager = layoutManager1
        val mAdapter1 = RecommendedRecyclerViewAdapter()
        rec1.adapter = mAdapter1

        val rec2: RecyclerView = findViewById(R.id.recyclerViewTrending)
        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rec2.layoutManager = layoutManager2
        val mAdapter2 = TrendingRecyclerViewAdapter()
        rec2.adapter = mAdapter2

        val rec3: RecyclerView = findViewById(R.id.recyclerViewTopRated)
        val layoutManager3 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rec3.layoutManager = layoutManager3
        val mAdapter3 = TopRatedRecyclerViewAdapter()
        rec3.adapter = mAdapter3

        val searchButton = findViewById<CardView>(R.id.cardSearch)
        searchButton.setOnClickListener {
            val intent = Intent(this, SearchableActivity::class.java)
            this.startActivity(intent)
        }
        /*val toolBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)

        val searchButton = findViewById<Button>(R.id.button1)
        searchButton.setOnClickListener {
            val intent = Intent(this, SearchableActivity::class.java)
            this.startActivity(intent)
        }*/
    }

    private fun setupBottomNavigationView(){

        val bottomNavigationView: BottomNavigationView =
            findViewById(R.id.bottomNavViewBar)


        val helperClass = BottomNavigationViewHelper()
        helperClass.enableNavigation(this, bottomNavigationView)

        val menu: Menu = bottomNavigationView.getMenu()
        val menuItem: MenuItem = menu.getItem(ACTIVITY_NUM)
        menuItem.setChecked(true)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                // task HERE
                return false
            }

        })

        return true
    }

}
