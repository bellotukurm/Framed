package com.example.framed.Pages

import android.content.DialogInterface
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import com.example.framed.Home.*
import com.example.framed.R
import com.example.framed.Utils.DBHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_game_page.*

class GamePage : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_page)

        val intent: Intent = getIntent()

        val title = findViewById<TextView>(R.id.game_title)
        title.text = intent.getStringExtra("GAME_TITLE")

        val addButton = findViewById<FloatingActionButton>(R.id.addFloatingActionButton)
        val menuButton = findViewById<FloatingActionButton>(R.id.menuFloatingActionButton)

        val DBHelperClass = DBHelper(this)
        val dbList = DBHelperClass.readGames()

        dbList.forEach{
            if(it.name.equals(title.text)){
                //addButton.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_add))
                //addButton.setImageResource(R.drawable.ic_more)
                //addButton.backgroundTintList = null
                addButton.isEnabled = false
                addButton.visibility = View.GONE
                menuButton.isEnabled = true
                menuButton.visibility = View.VISIBLE
            }
            println("${it.name}")
            println(title.text)
        }

        val genres = findViewById<TextView>(R.id.game_genres)
        val getGenres = intent.getStringExtra("GAME_GENRES")
        if(getGenres.length > 3){
            genres.text = getGenres!!.substring(0, getGenres.length-2)
        }

        val cover = findViewById<ImageView>(R.id.imageViewCover)
        val zURL = intent.getStringExtra("GAME_COVER")
        if(zURL == ""){
        }else{
            println("hsash" + zURL)
            Picasso.with(this).load("https:$zURL").into(cover)
        }

        addButton.setOnClickListener{
            val toStore = Game3(intent.getStringExtra("GAME_TITLE"), getGenres, zURL,
                intent.getStringExtra("GAME_DEVELOPERS"), intent.getStringExtra("GAME_CONSOLES"),
                intent.getLongExtra("GAME_RD", 0), intent.getIntExtra("GAME_AGE_RATING",0),
                intent.getStringExtra("GAME_SUMMARY"))
            DBHelperClass.addGame(toStore)
            DBHelperClass.readGames()

            addButton.isEnabled = false
            addButton.visibility = View.GONE
            menuButton.isEnabled = true
            menuButton.visibility = View.VISIBLE

            Toast.makeText(this, "Game Added", Toast.LENGTH_SHORT).show()
        }

        menuButton.setOnClickListener {
            val pop = PopupMenu(this, menuButton)
            pop.menuInflater.inflate(R.menu.game_page_menu, pop.menu)

            pop.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener(){
                onOptionsItemSelected(it)
            })
            pop.show()
        }





        val rd = findViewById<TextView>(R.id.textViewReleaseDate)
        val unixRD = (intent.getLongExtra("GAME_RD",0))

        val current = System.currentTimeMillis()/1000L
        var releaseDate = ""
        val sdf = java.text.SimpleDateFormat("dd' 'MMMM' 'yyyy")
        val date = java.util.Date(unixRD * 1000)
        if(sdf.format(date)== "01 January 1970"){
            println("date is null")
            rd.text = "TBA"
        }else{
            releaseDate = sdf.format(date)
            rd.text = releaseDate
        }


        val consoles = findViewById<TextView>(R.id.textViewConsoles)
        val getConsoles = intent.getStringExtra("GAME_CONSOLES")
        if(getConsoles.length > 2){
            consoles.text = getConsoles!!.substring(0, getConsoles.length-2)
        }

        val summary = findViewById<TextView>(R.id.textViewSummary)
        summary.text = intent.getStringExtra("GAME_SUMMARY")

        val developers = findViewById<TextView>(R.id.textViewDevelopers)
        val getDevelopers = intent.getStringExtra("GAME_DEVELOPERS")
        if(getConsoles.length > 3){
            developers.text = getDevelopers!!.substring(0, getDevelopers.length-2)
        }

        val ageRating = findViewById<ImageView>(R.id.imageViewAgeRating)
        val getAgeRating = intent.getIntExtra("GAME_AGE_RATING",0)
        when (getAgeRating) {
            0 -> ageRating.setImageResource(R.drawable.ic_rp)
            1 -> ageRating.setImageResource(R.drawable.three)
            2 -> ageRating.setImageResource(R.drawable.seven)
            3 -> ageRating.setImageResource(R.drawable.twelve)
            4 -> ageRating.setImageResource(R.drawable.sixteen)
            5 -> ageRating.setImageResource(R.drawable.eighteen)
            6 -> ageRating.setImageResource(R.drawable.ic_rp)
            7 -> ageRating.setImageResource(R.drawable.ec)
            8 -> ageRating.setImageResource(R.drawable.ic_e1)
            9 -> ageRating.setImageResource(R.drawable.ic_e10)
            10 -> ageRating.setImageResource(R.drawable.ic_t)
            11 -> ageRating.setImageResource(R.drawable.ic_m)
            12 -> ageRating.setImageResource(R.drawable.ic_ao)
            else -> {
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menuSave -> {Toast.makeText(this, "Save Presssed", Toast.LENGTH_SHORT).show()
                return true}
            R.id.menuRemove -> {
                val alert = AlertDialog.Builder(this)
                alert.setTitle("Confirm removal")

                alert.setPositiveButton("Remove", { dialogInterface: DialogInterface, i: Int ->
                    val DBHelperClass = DBHelper(this)
                    val title = findViewById<TextView>(R.id.game_title)
                    val dbList = DBHelperClass.readGames()

                    val addButton = findViewById<FloatingActionButton>(R.id.addFloatingActionButton)
                    val menuButton = findViewById<FloatingActionButton>(R.id.menuFloatingActionButton)

                    dbList.forEach{
                        if(it.name.equals(title.text)){
                            DBHelperClass.deleteGame(it.id)
                            menuButton.isEnabled = false
                            menuButton.visibility = View.GONE
                            addButton.isEnabled = true
                            addButton.visibility = View.VISIBLE
                            Toast.makeText(this, "Game Removed", Toast.LENGTH_SHORT).show()

                            val intent = Intent(this, HomeActivity::class.java )
                            startActivity(intent)
                        }
                    }
                })

                alert.setNegativeButton("Leave", { dialogInterface: DialogInterface, i: Int -> })
                alert.show()

                return true}
            else -> return super.onOptionsItemSelected(item)
        }
    }

}
