package com.example.framed.Home

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Pages.GamePage
import com.example.framed.R
import com.example.framed.Utils.Base
import com.example.framed.Utils.Game2
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_upcoming.view.*


class UpcomingRecyclerViewAdapter(val homeFeed: List<Game2>): RecyclerView.Adapter<UpcomingRecyclerViewAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_upcoming, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return homeFeed.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val game = homeFeed.get(position)
        holder.itemView.textView_upcoming_game_name.text = game.name

        //Show number of days before a game is released
        val current = System.currentTimeMillis()/1000L
        if(game.first_release_date == 0L){
            holder.itemView.textView_upcoming_date.text = "TBA"
            holder.itemView.textView_upcoming_days.text =""
        }else{
            var unixAfter = game.first_release_date - current
            val gameDate = java.util.Date(game.first_release_date * 1000)
            val currentDate = java.util.Date(current * 1000)
            val diffInDays = ((gameDate.time - currentDate.time)/(1000 * 60 * 60 * 24))
            println("$currentDate  $gameDate  $diffInDays")
            holder.itemView.textView_upcoming_date.text = diffInDays.toString()
        }


        val thumbnailImageView = holder?.itemView?.upcoming_game_image

        //set cover image of upcoming game object
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

        //Passing info to gamepage on upcoming game object clicked
        holder.itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View){

                val intent = Intent(v.context, GamePage::class.java )
                intent.putExtra("GAME_ID",game.id)
                intent.putExtra("GAME_TITLE",game.name)
                intent.putExtra("GAME_GENRES",genres)
                intent.putExtra("GAME_COVER",zURL)
                intent.putExtra("GAME_RD",game.first_release_date)
                intent.putExtra("GAME_CONSOLES",consoles)
                intent.putExtra("GAME_SUMMARY",game.summary)
                intent.putExtra("GAME_DEVELOPERS",game.involved_companies)
                intent.putExtra("GAME_AGE_RATING",game.age_ratings)
                intent.putExtra("GAME_PLAYLISTS", game.playlists)

                v.context.startActivity(intent)
            }
        })

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {}
}