package com.example.framed.Home



import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.R
import com.example.framed.Utils.DBHelper
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.util.Calendar


class HomeFragment: Fragment(){
    private val TAG = "HomeFragment"
    private lateinit var rec: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.fragment_home, container, false)
        rec = view.findViewById(R.id.recyclerViewHome)
        rec.layoutManager = LinearLayoutManager(context)
//        rec.adapter = HomeRecyclerViewAdapter()


        fetchReleased()

        return view
    }

    fun fetchReleased(){
        val dbHelperClass = DBHelper(activity!!.applicationContext)
        val dbList = dbHelperClass.readGames()

        val games: MutableList<Game2> = arrayListOf()
        dbList.forEach{
            val current = System.currentTimeMillis()/1000L
            if(current > it.first_release_date && it.first_release_date != 0L ){
                games.add(Game2(it.id,it.name,it.genres,it.cover, it.involved_companies,
                    it.platforms, it.first_release_date,it.age_ratings,it.summary))
                println("zeku" + it.first_release_date)
            }
        }

        activity?.runOnUiThread{
            rec.adapter = HomeRecyclerViewAdapter(games)
        }
    }

    fun fetchJson(){
        /*println("attempting to fetch JSON")

        val url = "https://api-v3.igdb.com/games/?fields=name,genres.name,cover.url," +
                "involved_companies.company.name,platforms.name,first_release_date," +
                "age_ratings.rating,summary,screenshots.url&order=popularity:desc"

        val request = Request.Builder().url(url)
            .addHeader("user-key", "b5ea467d2840a0b342df1d8b049b3ad4")
            .addHeader("Accept", "application/json")
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                println("np")
                val body = response.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val collectionType: Type = object :
                    TypeToken<Collection<Game?>?>() {}.type
                val games: List<Game> = gson.fromJson(body, collectionType)

                activity?.runOnUiThread{
                    rec.adapter = HomeRecyclerViewAdapter(games)
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("iFail")
            }
        })*/
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}


