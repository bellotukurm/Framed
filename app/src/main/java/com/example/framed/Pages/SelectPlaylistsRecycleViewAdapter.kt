package com.example.framed.Home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Pages.GamePage
import com.example.framed.Profile.PlaylistPage
import com.example.framed.R
import com.example.framed.Utils.DBHelper
import com.example.framed.Utils.Game2
import com.example.framed.Utils.Playlist
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.customize_item.view.*
import kotlinx.android.synthetic.main.item_game.view.*
import kotlinx.android.synthetic.main.playlist_item.view.*

class SelectPlaylistsRecyclerViewAdapter(val playlistsFeed: List<Playlist>, val game: Game2): RecyclerView.Adapter<SelectPlaylistsRecyclerViewAdapter.CustomViewHolder>() {

    val checkedList: MutableList<Playlist> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.customize_item, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return playlistsFeed.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var playlist = playlistsFeed.get(position)
        holder.itemView.customize_playlist_name.text = playlist.name

        println("z games playlist" + game.playlists)

        if(game.playlists.contains(playlist.id.toString())){
            println("lariat")
            holder.itemView.customize_checkBox.isChecked = true
            if(!checkedList.contains(playlist)){
                checkedList.add(playlist)
            }
        }else{
            holder.itemView.customize_checkBox.isChecked = false
        }

        holder.itemView.customize_checkBox.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View){
                holder.itemView.customize_checkBox.isChecked = !holder.itemView.customize_checkBox.isChecked
                if(!holder.itemView.customize_checkBox.isChecked){
                    if(!checkedList.contains(playlist)){
                        checkedList.add(playlist)
                    }
                    holder.itemView.customize_checkBox.isChecked = true
                }else{
                    println("removed")
                    if(checkedList.contains(playlist)){
                        checkedList.remove(playlist)
                    }
                    holder.itemView.customize_checkBox.isChecked = false
                }
                println(holder.itemView.customize_checkBox.isChecked)
            }
        })
        /*if(!holder.itemView.customize_checkBox.isChecked){
            )
        }
        if(holder.itemView.customize_checkBox.isChecked){
            holder.itemView.customize_checkBox.setOnClickListener(object: View.OnClickListener {
                override fun onClick(v: View){

                }
            })
        }*/


    }

    fun getCheckedItems(): List<Playlist>{
        return checkedList
    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    }

}