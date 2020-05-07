package com.example.framed.Home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Pages.GamePage
import com.example.framed.Profile.PlaylistPage
import com.example.framed.R
import com.example.framed.Utils.Playlist
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_game.view.*
import kotlinx.android.synthetic.main.playlist_item.view.*

class PlaylistsRecyclerViewAdapter(val playlistsFeed: List<Playlist>): RecyclerView.Adapter<PlaylistsRecyclerViewAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.playlist_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return playlistsFeed.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        println(playlistsFeed.size)
        val playlist = playlistsFeed.get(position)
        holder.itemView.playlist_name.text = playlist.name

        holder.itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View){

                val intent = Intent(v.context, PlaylistPage::class.java )
                intent.putExtra("PLAYLIST_TITLE", playlist.name)
                intent.putExtra("PLAYLIST_ID", playlist.id)
                v.context.startActivity(intent)
            }
        })

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }
}