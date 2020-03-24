package com.example.framed.Home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Pages.GamePage
import com.example.framed.R
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_game.view.*

class HomeRecyclerViewAdapter(val homeFeed: List<Game2>): RecyclerView.Adapter<HomeRecyclerViewAdapter.CustomViewHolder>() {

    //abstract val mContext: Context
    val gameTitles = listOf("Cyberpunk","Sekiro","God of War","Death Stranding",
        "Cyberpunk","Sekiro","God of War","Death Stranding")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_game, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {

        return homeFeed.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
//        val gameTitle = gameTitles.get(position)
        println(homeFeed.size)
        val game = homeFeed.get(position)
        holder.itemView.textView_game_name.text = game.name

        val thumbnailImageView = holder?.itemView?.game_image

        Picasso.with(holder.itemView.context).isLoggingEnabled = true
        var zURL = game.cover
        zURL = zURL.replace("thumb","720p")
        println(zURL)
        Picasso.with(holder.itemView.context).load("https:$zURL").into(thumbnailImageView)

        var genres = game.genres

        var consoles = game.platforms

        var ageRating = 0
        if(game.age_ratings==null){
            println("is null")
        }else{
            ageRating = game.age_ratings
        }

        holder.itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View){
                /*val snackbar = Snackbar.make(v, "It worked", Snackbar.LENGTH_LONG)
                snackbar.show()
                Log.d("Snacky", "worked")*/

                val intent = Intent(v.context, GamePage::class.java )
                intent.putExtra("GAME_TITLE",game.name)
                intent.putExtra("GAME_GENRES",genres)
                intent.putExtra("GAME_COVER",zURL)
                intent.putExtra("GAME_RD",game.first_release_date)
                intent.putExtra("GAME_CONSOLES",consoles)
                intent.putExtra("GAME_SUMMARY",game.summary)
                intent.putExtra("GAME_DEVELOPERS",game.involved_companies)
                intent.putExtra("GAME_AGE_RATING",game.age_ratings)
                v.context.startActivity(intent)
            }
        })

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val v: View = LayoutInflater.from(mContext).inflate(R.layout)

    }
}