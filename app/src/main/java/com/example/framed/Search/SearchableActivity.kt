package com.example.framed.Search

import android.app.SearchManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Utils.Game
import com.example.framed.R
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

class SearchableActivity : AppCompatActivity() {
    private val TAG = "SearchFragment"
    private lateinit var rec: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searchable)

        val toolBar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolBar)

        val searchBar = supportActionBar
        searchBar!!.setDisplayHomeAsUpEnabled(true)

        val searchEditText = findViewById<EditText>(R.id.search_edit_text)
        searchEditText.selectAll()

        rec = findViewById(R.id.recyclerViewSearch)
        rec.layoutManager = LinearLayoutManager(this)
        fetchJson()

        val searchText = findViewById<EditText>(R.id.search_edit_text)
        searchText.addTextChangedListener(object : TextWatcher
        {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                searcher(s.toString())
            }

        })
        searchText.setOnEditorActionListener{s, actionId, event ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                searcher(s.text.toString())
                true
            } else {
                false
            }
        }


        // Verify the action and get the query
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                doMySearch(query)
            }
        }
    }

    private fun searcher(text: String) {
        println("0000000000")
        var url = "https://api-v3.igdb.com/games/?fields=name,genres.name,cover.url," +
                        "involved_companies.company.name,platforms.name,first_release_date," +
                        "age_ratings.rating,summary,screenshots.url,videos.video_id" +
                        "&search=pvxw&where%version_parent=null"
        //var url = "https://api-v3.igdb.com/games/?fields=name,genres.name,cover.url," +
                //"involved_companies.company.name,platforms.name,first_release_date," +
                //"age_ratings.rating,summary,screenshots.url&order=popularity:desc"
        url = url.replace("pvxw",text)

        val request = Request.Builder().url(url)
            .addHeader("user-key", "b5ea467d2840a0b342df1d8b049b3ad4")
            .addHeader("Accept", "application/json")
            .build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {

                val body = response.body?.string()
                //println("woosh" + body)

                var v = body?.split("\n")
                var vm = v?.toMutableList()
                //For every line in the returned json
                for(i in 0..(vm?.size!!)-1){
                    //adding { to class of cover if does not have
                    if(vm.get(i).contains("\"cover\": {")){
                    }else if(vm.get(i).contains("\"cover\": ")){
                        vm.set(i, vm.get(i).replace("\"cover\":","\"cover\": {\n\"id\":"))
                        vm.get(i).plus(",")
                        val zy = "\"url\": \"\" \n},\n".plus(vm.get(i+1))
                        vm.set(i+1, zy)
                    }
                    if(vm.get(i).contains("\"screenshots\": [")){
                        if(vm.get(i+1).contains("{")){
                        }else{
                            vm.set(i+1, "")
                        }
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

                runOnUiThread{
                    rec.adapter = SearchRecyclerViewAdapter(games)
                }

            }
            override fun onFailure(call: Call, e: IOException) {
                println("iFail")
            }
        })
    }

    fun fetchJson(){
        println("attempting to fetch JSON")

        //val url = "https://api-v3.igdb.com/games/?fields=name,genres.name,cover.url,franchise&order=popularity:desc"
        var url = "https://api-v3.igdb.com/games/?fields=name,genres.name,cover.url," +
                "involved_companies.company.name,platforms.name,first_release_date," +
                "age_ratings.rating,summary,screenshots.url&where%version_parent=null&order=popularity:desc"

        val request = Request.Builder().url(url)
            .addHeader("user-key", "b5ea467d2840a0b342df1d8b049b3ad4")
            .addHeader("Accept", "application/json")
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
                    rec.adapter = SearchRecyclerViewAdapter(games)
                }

            }
            override fun onFailure(call: Call, e: IOException) {
                println("iFail")
            }
        })
    }

    private fun doMySearch(query: String) {}
}
