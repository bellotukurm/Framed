package com.example.framed.Profile

import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.framed.Pages.GamePage
import com.example.framed.R
import com.example.framed.Utils.BottomNavigationViewHelper
import com.example.framed.Utils.DBHelper
import com.google.android.material.bottomnavigation.BottomNavigationView

class ProfileActivity : AppCompatActivity() {

    private val ACTIVITY_NUM = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)


        Log.d("ProfileActivity","started")
        setupBottomNavigationView()

        val gamesCount = findViewById<TextView>(R.id.gamesCount)
        val playlistsCount = findViewById<TextView>(R.id.playlist_count)
        val dbHelperClass = DBHelper(this.applicationContext)
        gamesCount.text = dbHelperClass.countGames().toString()
        playlistsCount.text = dbHelperClass.countPlaylists().toString()

        val gamesButton = findViewById<ConstraintLayout>(R.id.games_button)
        gamesButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(v?.context, AllGamesPage::class.java)
                intent.putExtra("HINT_VALUE", "All")
                startActivity(intent)
            }
        })

        val playlistsButton = findViewById<ConstraintLayout>(R.id.playlists_button)
        playlistsButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intent = Intent(v?.context, PlaylistsPage::class.java)
                startActivity(intent)
            }
        })
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

    override fun onResume() {
        super.onResume()
        this.overridePendingTransition(0,0)
        setupBottomNavigationView()
    }
}
