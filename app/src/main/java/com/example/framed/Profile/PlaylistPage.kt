package com.example.framed.Profile

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Home.HomeActivity
import com.example.framed.R
import com.example.framed.Utils.DBHelper
import com.example.framed.Utils.Game2
import com.example.framed.Utils.Playlist
import com.google.android.material.floatingactionbutton.FloatingActionButton

class PlaylistPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_page)

        val intent: Intent = getIntent()
        val playlist: Playlist = Playlist(intent.getIntExtra("PLAYLIST_ID", 0),
            intent.getStringExtra("PLAYLIST_TITLE"))

        val title  = findViewById<TextView>(R.id.textView_playlist_name)
        title.text = playlist.name

        val dbHelperClass = DBHelper(this.applicationContext)
        val dbList = dbHelperClass.readGames()

        val deletePlaylist  = findViewById<ImageView>(R.id.delete_playlist)
        deletePlaylist.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {

                val alert = AlertDialog.Builder(v!!.context)
                alert.setTitle("Confirm removal")
                alert.setMessage("This playlist will be permanently deleted")

                alert.setPositiveButton("Remove", { dialogInterface: DialogInterface, i: Int ->
                    val DBHelperClass = DBHelper(v!!.context)
                    dbHelperClass.deletePlaylist(intent.getIntExtra("PLAYLIST_ID",0))
                    Toast.makeText(v.context, "Playlist Removed", Toast.LENGTH_SHORT).show()

                    val intent = Intent(v.context, PlaylistsPage::class.java )
                    startActivity(intent)
                })

                alert.setNegativeButton("Leave", { dialogInterface: DialogInterface, i: Int -> })
                alert.show()
                }
            })

        var rec = findViewById<RecyclerView>(R.id.recyclerViewAllPlaylistGames)
        rec.layoutManager = GridLayoutManager(this, 3)

        val games: MutableList<Game2> = arrayListOf()
        dbList.forEach{
            println("happening" + it.playlists)
            if(it.playlists.contains(playlist.id.toString())){
                games.add(
                    Game2(
                        it.id, it.name, it.genres, it.cover, it.involved_companies,
                        it.platforms, it.first_release_date, it.age_ratings, it.summary,
                        it.playlists
                    )
                )
            }
        }
        rec.adapter = GridAdapter(games)
    }

    override fun onRestart() {
        super.onRestart()
        finish()
        startActivity(intent)
    }
}
