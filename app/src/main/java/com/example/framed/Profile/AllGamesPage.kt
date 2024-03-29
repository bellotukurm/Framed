package com.example.framed.Profile

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Utils.Game2
import com.example.framed.R
import com.example.framed.Utils.DBHelper
import android.content.Intent
import android.widget.EditText
import android.widget.TextView

class AllGamesPage : AppCompatActivity() {
    var filt: String = "All"

    internal var lang = arrayOf("bb","jmm","kka","iao")
    //private lateinit var grid: GridView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_all_games_page)

        val intent: Intent = getIntent()
        filt = intent.getStringExtra("HINT_VALUE")

        val dbHelperClass = DBHelper(this.applicationContext)
        val dbList = dbHelperClass.readGames()

        var rec = findViewById<RecyclerView>(R.id.recyclerViewAllGames)
        rec.layoutManager = GridLayoutManager(this, 3)

        var title  = findViewById<TextView>(R.id.textView_all_games)
        title.text = filt + " Games"

        val choosePlatform = findViewById<EditText>(R.id.choose_platform)

        val games: MutableList<Game2> = arrayListOf()
        dbList.forEach{
            if(it.platforms.contains(filt)){
                games.add(
                    Game2(
                        it.id, it.name, it.genres, it.cover, it.involved_companies,
                        it.platforms, it.first_release_date, it.age_ratings, it.summary,
                        it.playlists
                    )
                )
            }
            else if(filt.equals("All")){
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

        var filterButton = findViewById<CardView>(R.id.filterButton)
        filterButton.setOnClickListener(object: View.OnClickListener{
            override fun onClick(v: View?) {
                openFilterDialog(filt);
            }
        })

    }

    fun openFilterDialog(filt: String){
        val fD = FilterDialog(filt)
        fD.show(supportFragmentManager, "filter dialog")
    }

    //Setting what happens when back is pressed on this page
    override fun onBackPressed() {
        super.onBackPressed()
        if(filt.equals("All")){
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
        else{
            val intent = Intent(this, AllGamesPage::class.java)
            intent.putExtra("HINT_VALUE", "All")
            startActivity(intent)

        }

    }

}
