package com.example.framed.Profile

import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.view.contains
import com.example.framed.Home.Game2
import com.example.framed.Pages.GamePage
import com.example.framed.R
import com.example.framed.Utils.DBHelper

class FilterDialog(var hinter: String): AppCompatDialogFragment (){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)

        val inflater = activity?.layoutInflater
        val view = inflater?.inflate(R.layout.layout_filter_dialog, null)

        val choosePlatform = view?.findViewById<EditText>(R.id.choose_platform)
        choosePlatform?.hint = hinter

        val dbHelperClass = DBHelper(activity!!.applicationContext)
        val dbList = dbHelperClass.readGames()
        val games: MutableList<Game2> = arrayListOf()

        builder.setView(view)
            .setTitle("Filter")
            .setPositiveButton("confirm", { dialogInterface: DialogInterface, i: Int ->
                println("hiint " + choosePlatform!!.hint as String)
                val intent = Intent(view?.context, AllGamesPage::class.java )
                intent.putExtra("HINT_VALUE", choosePlatform!!.hint as String)
                view?.context?.startActivity(intent)
            })
            .setNegativeButton("cancel", { dialogInterface: DialogInterface, i: Int ->})


        choosePlatform?.setOnClickListener{
            val pop = PopupMenu(view.context, choosePlatform)
            //pop.menu.add(Menu.NONE, 1, Menu.NONE, "Item name")
            //pop.menu.add(Menu.NONE, 1, Menu.NONE, "zubzub")
            //pop.menuInflater.inflate(R.menu.filter_menu, pop.menu)



            pop.menu.add("All")

            var uniqueConsoles: MutableList<String> = ArrayList()
            dbList.forEach{
                var toSplitString = it.platforms.substring(0, it.platforms.length-3)
                var toSplit = toSplitString.split(" • ")
                println(toSplit + " to spliit")
                toSplit.forEach{
                    if(uniqueConsoles.contains(it) || uniqueConsoles.contains(it+" ") || uniqueConsoles.contains(it.substring(0, it.length-1)))
                        {println(it + "skipped")}
                    else{
                        uniqueConsoles.add(it)
                        pop.menu.add(it)
                        println(it +"added")
                    }
                }
            }

            pop.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener(){
                choosePlatform.hint = it.title
                onOptionsItemSelected(it)
            })
            pop.show()
        }
        return builder.create()
    }
}