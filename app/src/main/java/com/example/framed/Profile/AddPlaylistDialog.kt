package com.example.framed.Profile

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.framed.Utils.Game2
import com.example.framed.R
import com.example.framed.Utils.DBHelper

class AddPlaylistDialog: AppCompatDialogFragment (){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.layout_add_playlist_dialog, null)

        val namePlaylist = view?.findViewById<EditText>(R.id.name_playlist)

        val dbHelperClass = DBHelper(activity!!.applicationContext)
        //val dbList = dbHelperClass.readGames()

        builder.setView(view)
            .setTitle("Add Playlist")
            .setPositiveButton("confirm", { dialogInterface: DialogInterface, i: Int ->
                dbHelperClass.addPlaylist(namePlaylist?.text.toString())
                val intent = Intent(view?.context, PlaylistsPage::class.java )
                view?.context?.startActivity(intent)
            })
            .setNegativeButton("cancel", { dialogInterface: DialogInterface, i: Int ->})


        return builder.create()
    }
}