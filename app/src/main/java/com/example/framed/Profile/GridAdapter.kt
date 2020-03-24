package com.example.framed.Profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Home.Game2
import com.example.framed.Home.HomeRecyclerViewAdapter
import com.example.framed.Pages.GamePage
import com.example.framed.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_all_games_page.view.*
import kotlinx.android.synthetic.main.grid_item.view.*
import kotlinx.android.synthetic.main.item_game.view.*

class GridAdapter(val homeFeed: List<Game2>): RecyclerView.Adapter<GridAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.grid_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return homeFeed.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        println(homeFeed.size)
        val game = homeFeed.get(position)

        val thumbnailImageView = holder?.itemView?.grid_image_cover

        Picasso.with(holder.itemView.context).isLoggingEnabled = true
        var zURL = game.cover
        zURL = zURL.replace("thumb","720p")
        println(zURL)
        Picasso.with(holder.itemView.context).load("https:$zURL").resize(504,720).into(thumbnailImageView)

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