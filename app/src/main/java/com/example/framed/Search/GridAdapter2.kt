package com.example.framed.Profile

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Utils.Game2
import com.example.framed.Pages.GamePage
import com.example.framed.R
import com.example.framed.Utils.Game
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_item.view.*

class GridAdapter2(val homeFeed: List<Game>): RecyclerView.Adapter<GridAdapter2.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.grid_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return homeFeed.size
    }

    override fun onBindViewHolder(holder: GridAdapter2.CustomViewHolder, position: Int) {
        println(homeFeed.size)
        val game = homeFeed.get(position)

        val thumbnailImageView = holder?.itemView?.grid_image_cover

        Picasso.with(holder.itemView.context).isLoggingEnabled = true
        var zURL = game.cover.url
        zURL = zURL.replace("thumb","720p")
        println(zURL)
        Picasso.with(holder.itemView.context).load("https:$zURL").resize(504,720).into(thumbnailImageView)

        var genres = ""
        if(game.genres != null){
            game.genres.forEach { genres += it.name + "|" + it.id.toString() + "/"}
        }


        var consoles = ""
        if(game.platforms != null){
            game.platforms.forEach{consoles += it.name + "|" + it.id.toString() + "/"}
        }


        var developers = "by "
        if(game.involved_companies != null){
            game.involved_companies.forEach{developers += it.company.name + "|" + it.id.toString() + "/"}
            game.involved_companies.forEach { println(it.company.name) }
        }

        println(game.first_release_date)

        var ageRating = 0
        if(game.age_ratings==null){
            println("is null")
        }else{
            ageRating = game.age_ratings.get(0).rating
        }
        println(ageRating)

        var releaseDate = ""
        /*if(game.first_release_date != null){
            game.involved_companies.forEach{developers += it.company.name + " • "}
            game.involved_companies.forEach { println(it.company.name) }
        }*/
        val sdf = java.text.SimpleDateFormat("dd' 'MMMM' 'yyyy")
        val date = java.util.Date(game.first_release_date * 1000)
        println("looooopy" + game.first_release_date)
        if(sdf.format(date)== "01 January 1970"){
            println("date is null")
        }else{
            releaseDate = sdf.format(date)
            println("zzzz" + sdf.format(date))
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
                intent.putExtra("GAME_DEVELOPERS",developers)
                intent.putExtra("GAME_AGE_RATING",ageRating)
                v.context.startActivity(intent)
            }
        })
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val v: View = LayoutInflater.from(mContext).inflate(R.layout)
    }

}