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
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Home.HomeActivity
import com.example.framed.Pages.GamePage
import com.example.framed.RecommendedRecyclerViewAdapter
import com.example.framed.R
import com.example.framed.TopRatedRecyclerViewAdapter
import com.example.framed.TrendingRecyclerViewAdapter
import com.example.framed.Utils.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_search.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException
import java.lang.reflect.Type

class DiscoverActivity : AppCompatActivity() {

    private val ACTIVITY_NUM = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        Log.d("DiscoverActivity","started")
        setupBottomNavigationView()

        println("discoverOncreate")
        editorsChoiceButton()

        recommendationAlgorithm()


        /*val rec2: RecyclerView = findViewById(R.id.recyclerViewTrending)
        val layoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rec2.layoutManager = layoutManager2
        val mAdapter2 = TrendingRecyclerViewAdapter()
        rec2.adapter = mAdapter2

        val rec3: RecyclerView = findViewById(R.id.recyclerViewTopRated)
        val layoutManager3 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rec3.layoutManager = layoutManager3
        val mAdapter3 = TopRatedRecyclerViewAdapter()
        rec3.adapter = mAdapter3*/

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

    //: List<Game>
    fun recommendationAlgorithm(){

        //code to get all games
        val DBHelperClass = DBHelper(this)
        val dbList = DBHelperClass.readGames()

        val rec1: RecyclerView = findViewById(R.id.recyclerViewRecommended)
        val layoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rec1.layoutManager = layoutManager1

        val platrec = getPlatformRecurrence(dbList)
        var maxplatRec = 0
        val maxPlatformsID: MutableList<String> = arrayListOf()

        platrec.forEach {
            if(it.recurrence > maxplatRec){
                maxplatRec = it.recurrence
            }
        }
        platrec.forEach {
            if(it.recurrence == maxplatRec){
                val zSplit = it.name.split("|")
                maxPlatformsID.add(zSplit.get(1))
                println("max platforms " + zSplit.get(1))
            }
        }

        val genrerec = getGenreRecurrence(dbList)
        var maxgenreRec = 0
        val maxGenresID: MutableList<String> = arrayListOf()

        genrerec.forEach {
            if(it.recurrence >= maxgenreRec){
                maxgenreRec = it.recurrence
            }
        }
        genrerec.forEach {
            if(it.recurrence == maxgenreRec){
                val zSplit = it.name.split("|")
                maxGenresID.add(zSplit.get(1))
                println("max genres " + zSplit.get(1))
            }
        }

        val devrec = getDeveloperRecurrence(dbList)
        var maxdevRec = 0
        val maxDevID: MutableList<String> = arrayListOf()

        devrec.forEach {
            if(it.recurrence >= maxdevRec){
                maxdevRec = it.recurrence
            }
        }
        devrec.forEach {
            if(it.recurrence == maxdevRec){
                val zSplit = it.name.split("|")
                maxDevID.add(zSplit.get(1))
                println("max developers " + zSplit.get(1))
            }
        }

        if(maxPlatformsID.isNotEmpty() && maxGenresID.isNotEmpty()){

            val firstMostPlatform = maxPlatformsID.get(0)
            println("first most platform " + maxPlatformsID.get(0))

            val firstMostGenre = maxGenresID.get(0)
            println("first most genres " + maxGenresID.get(0))

            val finalGames: MutableList<Game> = arrayListOf()

            var url = "https://api-v3.igdb.com/games"
            val body:RequestBody = RequestBody.create("application/json".toMediaType(),
                "fields name,genres.name,cover.url,involved_companies.company.name," +
                        "platforms.name,first_release_date,age_ratings.rating,summary," +
                        "screenshots.url;" +
                        "where platforms=($firstMostPlatform) & genres=($firstMostGenre) &" +
                        " rating > 80;")
            val request = Request.Builder().url(url)
                .addHeader("user-key", "b5ea467d2840a0b342df1d8b049b3ad4")
                .addHeader("Accept", "application/json")
                .post(body)
                .build()

            // using OkHttpClient to send get request
            val client = OkHttpClient()
            client.newCall(request).enqueue(object: Callback {
                override fun onResponse(call: Call, response: Response) {
                    //the return of the request as body
                    val body = response.body?.string()
                    val gson = GsonBuilder().create()
                    val collectionType: Type = object :
                        TypeToken<Collection<Game?>?>() {}.type
                    //using Gson to parse into a list of Games
                    val games: List<Game> = gson.fromJson(body, collectionType)

                    runOnUiThread{
                        val mAdapter1 = RecommendedRecyclerViewAdapter(games)
                        rec1.adapter = mAdapter1
                    }
                }
                override fun onFailure(call: Call, e: IOException) {
                    println("iFail")
                }
            })

            val seeAll = findViewById<TextView>(R.id.textView_seeAll_rec)
            val intent = Intent(this, SeeAllRecommendedActivity::class.java)
            //intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
            seeAll.setOnClickListener {
                it.context.startActivity(intent)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        this.overridePendingTransition(0,0)
        setupBottomNavigationView()
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

    private fun editorsChoiceButton(){
        val editorsChoiceButton = findViewById<ImageView>(R.id.imageView_editors_choice)

        var url = "https://api-v3.igdb.com/games/26192?fields=name,genres.name,cover.url," +
                "involved_companies.company.name,platforms.name,first_release_date," +
                "age_ratings.rating,summary,screenshots.url,videos.video_id,similar_games.*"

        val request = Request.Builder().url(url)
            .addHeader("user-key", "b5ea467d2840a0b342df1d8b049b3ad4")
            .addHeader("Accept", "application/json")
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {

                val body = response.body?.string()

                var v = body?.split("\n")
                var vm = v?.toMutableList()
                for(i in 0..(vm?.size!!)-1){
                    if(vm.get(i).contains("\"cover\": {")){
                    }else if(vm.get(i).contains("\"cover\": ")){
                        vm.set(i, vm.get(i).replace("\"cover\":","\"cover\": {\n\"id\":"))
                        vm.get(i).plus(",")
                        val zy = "\"url\": \"\" \n},\n".plus(vm.get(i+1))
                        vm.set(i+1, zy)
                    }

                }
                var formattedBody = ""
                vm.forEach{
                    formattedBody += "$it\n"
                }
                println(formattedBody)


                val gson = GsonBuilder().create()

                val collectionType: Type = object :
                    TypeToken<Collection<Game?>?>() {}.type
                val games: List<Game> = gson.fromJson(formattedBody, collectionType)
                val game: Game = games.get(0)
                var genres = ""
                if(game.genres != null){
                    game.genres.forEach { genres += it.name + "|" + it.id.toString() + "/"}
                }

                var zURL = ""
                if(game.cover != null){
                    zURL = game.cover.url
                    zURL = zURL.replace("thumb","720p")
                }

                var consoles = ""
                if(game.platforms != null){
                    game.platforms.forEach{consoles += it.name + "|" + it.id.toString() + "/"}
                }

                var developers = "by "
                if(game.involved_companies != null){
                    game.involved_companies.forEach{developers += it.company.name + "|" + it.id.toString() + "/"}
                }

                var ageRating = 0
                if(game.age_ratings==null){
                    println("is null")
                }else{
                    ageRating = game.age_ratings.get(0).rating
                }

                runOnUiThread{
                    editorsChoiceButton.setOnClickListener {
                        val intent = Intent(it.context, GamePage::class.java )
                        intent.putExtra("GAME_TITLE",game.name)
                        intent.putExtra("GAME_GENRES",genres)
                        intent.putExtra("GAME_COVER",zURL)
                        intent.putExtra("GAME_RD",game.first_release_date)
                        intent.putExtra("GAME_CONSOLES",consoles)
                        intent.putExtra("GAME_SUMMARY",game.summary)
                        intent.putExtra("GAME_DEVELOPERS",developers)
                        intent.putExtra("GAME_AGE_RATING",ageRating)
                        startActivity(intent)

                    }
                }

            }
            override fun onFailure(call: Call, e: IOException) {
                println("iFail")
            }
        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, HomeActivity::class.java )
        startActivity(intent)
        overridePendingTransition(0,0)
    }

}
