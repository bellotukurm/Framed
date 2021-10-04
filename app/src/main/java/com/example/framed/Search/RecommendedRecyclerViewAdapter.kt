package com.example.framed

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Pages.GamePage
import com.example.framed.Utils.Game
import com.example.framed.Utils.Game2
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.grid_item.view.*
import kotlinx.android.synthetic.main.item_game.view.*
import kotlinx.android.synthetic.main.item_search.view.*
import kotlinx.android.synthetic.main.search_rec_item.view.*

class RecommendedRecyclerViewAdapter(val recommendedFeed: List<Game>): RecyclerView.Adapter<RecommendedRecyclerViewAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): RecommendedRecyclerViewAdapter.CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.search_rec_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    //abstract val mContext: Context

    override fun getItemCount(): Int {
        return recommendedFeed.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val game = recommendedFeed.get(position)

        val thumbnailImageView = holder?.itemView?.search_rec_image_cover

        Picasso.with(holder.itemView.context).isLoggingEnabled = true

        var zURL = ""
        if(game.cover != null){
            zURL = game.cover.url
            zURL = zURL.replace("thumb","720p")
            println(zURL)
            Picasso.with(holder.itemView.context).load("https:$zURL").resize(504,720).into(thumbnailImageView)
        }

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
    }
}