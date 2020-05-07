package com.example.framed.Profile

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.framed.Home.SelectPlaylistsRecyclerViewAdapter
import com.example.framed.Utils.Game2
import com.example.framed.R
import com.example.framed.Utils.DBHelper

class CustomizePlaylistsDialog(val game: Game2): AppCompatDialogFragment (){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.layout_select_playlist, null)

        val dbHelperClass = DBHelper(activity!!.applicationContext)


        var rec = view?.findViewById<RecyclerView>(R.id.recyclerViewAllPlaylistsCheck)
        rec?.layoutManager = LinearLayoutManager(view?.context)

        var recGame = game
        val dbList = dbHelperClass.readGames()

        dbList.forEach {
            println("z game id" + it.id + game.id)
            if(it.id == game.id){
                recGame = it
                println(it.playlists)
            }
        }

        val dbPlaylistList = dbHelperClass.readPlaylists()
        rec?.adapter = SelectPlaylistsRecyclerViewAdapter(dbPlaylistList, recGame)
        builder.setView(view)
            .setTitle("Customize Playlist")
            .setPositiveButton("confirm", { dialogInterface: DialogInterface, i: Int ->
                game.playlists = ""
                (rec?.adapter as SelectPlaylistsRecyclerViewAdapter).getCheckedItems().forEach {
                    game.playlists += it.id.toString() + ","
                    println(it.name)
                }
                println(dbHelperClass.updateGamePlaylists(game))
                dbList.forEach {
                    println("second" + it.name + it.playlists)
                }
            })
            .setNegativeButton("cancel", { dialogInterface: DialogInterface, i: Int ->})




        return builder.create()
    }
}