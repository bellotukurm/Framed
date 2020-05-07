package com.example.framed.Search

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Home.HomeActivity
import com.example.framed.Profile.GridAdapter
import com.example.framed.Profile.GridAdapter2
import com.example.framed.R
import com.example.framed.RecommendedRecyclerViewAdapter
import com.example.framed.Utils.*
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import java.io.IOException
import java.lang.reflect.Type

class SeeAllRecommendedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_see_all_recommended)

        println("seeAllOncreate")

        var rec = findViewById<RecyclerView>(R.id.recyclerViewSeeAllRecGames)
        rec.layoutManager = GridLayoutManager(this, 3)

        val DBHelperClass = DBHelper(this)
        val dbList = DBHelperClass.readGames()

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

        val firstMostPlatform = maxPlatformsID.get(0)
        println("first most platform " + maxPlatformsID.get(0))

        val firstMostGenre = maxGenresID.get(0)
        println("first most genres " + maxGenresID.get(0))

        var url = "https://api-v3.igdb.com/games"
        val body:RequestBody = RequestBody.create("application/json".toMediaType(),
            "fields name,genres.name,cover.url,involved_companies.company.name," +
                    "platforms.name,first_release_date,age_ratings.rating,summary," +
                    "screenshots.url;" +
                    "where platforms=($firstMostPlatform) & genres=($firstMostGenre) &" +
                    " rating > 80; limit 40;")
        val request = Request.Builder().url(url)
            .addHeader("user-key", "b5ea467d2840a0b342df1d8b049b3ad4")
            .addHeader("Accept", "application/json")
            .post(body)
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {

                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val collectionType: Type = object :
                    TypeToken<Collection<Game?>?>() {}.type
                val games: List<Game> = gson.fromJson(body, collectionType)

                runOnUiThread{
                    rec.adapter = GridAdapter2(games)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("iFail")
            }
        })
    }

    override fun onResume() {
        super.onResume()
        this.overridePendingTransition(0,0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, DiscoverActivity::class.java)
        startActivity(intent)
    }

}
