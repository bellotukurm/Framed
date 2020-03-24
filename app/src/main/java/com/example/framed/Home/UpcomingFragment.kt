package com.example.framed.Home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.R
import com.example.framed.Utils.DBHelper

class UpcomingFragment: Fragment(){

    private val TAG = "UpcomingFragment"
    private lateinit var re: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_upcoming, container, false)
        re = view.findViewById(R.id.recyclerViewHome)
        re.layoutManager = LinearLayoutManager(context)
//        rec.adapter = UpcomingRecyclerViewAdapter()

        fetchReleased()
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private fun fetchReleased(){
        val dbHelperClass = DBHelper(activity!!.applicationContext)
        val dbList = dbHelperClass.readGames()

        val games: MutableList<Game2> = arrayListOf()

        dbList.forEach{
            val current = System.currentTimeMillis()/1000L
            if( it.first_release_date > current || it.first_release_date == 0L){
                games.add(Game2(it.id,it.name,it.genres,it.cover, it.involved_companies,
                    it.platforms, it.first_release_date,it.age_ratings,it.summary))
            }
        }

        re.adapter = UpcomingRecyclerViewAdapter(games)

    }
}