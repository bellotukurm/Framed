package com.example.framed.Profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Home.PlaylistsRecyclerViewAdapter
import com.example.framed.R
import com.example.framed.Utils.DBHelper

class PlaylistsPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlists_page)

        val addPlaylistButton = findViewById<ImageView>(R.id.add_playlist)
        addPlaylistButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                openAddPlaylistDialog()
            }
        })

        var rec = findViewById<RecyclerView>(R.id.recyclerViewAllPlaylists)
        rec.layoutManager = LinearLayoutManager(this)

        val dbHelperClass = DBHelper(this.applicationContext)
        val dbPlaylistList = dbHelperClass.readPlaylists()

        rec.adapter = PlaylistsRecyclerViewAdapter(dbPlaylistList)

    }


    private fun openAddPlaylistDialog() {
        val addPlaylistDialog = AddPlaylistDialog()
        addPlaylistDialog.show(supportFragmentManager, "add playlist dialog")
    }

    override fun onBackPressed() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }
}
