package com.example.framed.Pages

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.*
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.framed.Profile.CustomizePlaylistsDialog
import com.example.framed.R
import com.example.framed.Utils.Base
import com.example.framed.Utils.DBHelper
import com.example.framed.Utils.Game2
import com.example.framed.Utils.Game3
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso

class GamePage : AppCompatActivity(){

    private val myFilter: IntentFilter = IntentFilter(Intent.ACTION_ANSWER)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_page)

        val intent: Intent = getIntent()

        val title = findViewById<TextView>(R.id.game_title)
        title.text = intent.getStringExtra("GAME_TITLE")

        val addButton = findViewById<FloatingActionButton>(R.id.addFloatingActionButton)
        val menuButton = findViewById<FloatingActionButton>(R.id.menuFloatingActionButton)

        //Getting all games in the database
        val DBHelperClass = DBHelper(this)
        val dbList = DBHelperClass.readGames()

        //If game exists in the database
        dbList.forEach{
            if(it.name.equals(title.text)){

                addButton.isEnabled = false
                addButton.visibility = View.GONE
                menuButton.isEnabled = true
                menuButton.visibility = View.VISIBLE
            }
        }

        //Making the cover of the game show
        val cover = findViewById<ImageView>(R.id.imageViewCover)
        val zURL = intent.getStringExtra("GAME_COVER")
        if(zURL == ""){
        }else{
            Picasso.with(this).load("https:$zURL").into(cover)
        }

        //Setting what happens when add button is pressed
        addButton.setOnClickListener{
            val toStore = Game3(
                intent.getStringExtra("GAME_TITLE"), intent.getStringExtra("GAME_GENRES"), zURL,
                intent.getStringExtra("GAME_DEVELOPERS"), intent.getStringExtra("GAME_CONSOLES"),
                intent.getLongExtra("GAME_RD", 0), intent.getIntExtra("GAME_AGE_RATING", 0),
                intent.getStringExtra("GAME_SUMMARY"), ""
            )
            DBHelperClass.addGame(toStore)
            DBHelperClass.readGames()

            addButton.isEnabled = false
            addButton.visibility = View.GONE
            menuButton.isEnabled = true
            menuButton.visibility = View.VISIBLE

            Toast.makeText(this, "Game Added", Toast.LENGTH_SHORT).show()
        }

        //Setting what happens when menu button is pressed
        menuButton.setOnClickListener {
            val pop = PopupMenu(this, menuButton)
            pop.menuInflater.inflate(R.menu.game_page_menu, pop.menu)

            pop.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener(){
                onOptionsItemSelected(it)
            })
            pop.show()
        }

        //Getting games unix timestamp and converting to release date
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

        //Getting genres of game and splitting the genres names from id
        val genres = findViewById<TextView>(R.id.game_genres)
        val getGenres = intent.getStringExtra("GAME_GENRES")
        if(getGenres.length > 3){
            var gamepageGenres = ""
            var toSplitString = getGenres!!.substring(0, getGenres.length-1)
            var toSplit = toSplitString.split("/")
            toSplit.forEach {
                var itemContents = it.split("|")
                gamepageGenres += itemContents.get(0) + " • "
            }
            genres.text = gamepageGenres.substring(0, gamepageGenres.length-2)
        }

        //Getting consoles of game and splitting the consoles names from id
        val consoles = findViewById<TextView>(R.id.textViewConsoles)
        val getConsoles = intent.getStringExtra("GAME_CONSOLES")
        if(getConsoles.length > 2){
            var gamepageConsole = ""
            var toSplitString = getConsoles!!.substring(0, getConsoles.length-1)
            var toSplit = toSplitString.split("/")
            toSplit.forEach {
                var itemContents = it.split("|")
                gamepageConsole += itemContents.get(0) + " • "
            }
            consoles.text = gamepageConsole.substring(0, gamepageConsole.length-2)
        }

        //Getting summary and setting it in game page
        val summary = findViewById<TextView>(R.id.textViewSummary)
        summary.text = intent.getStringExtra("GAME_SUMMARY")

        //Getting developers of game and splitting the developers names from id
        val developers = findViewById<TextView>(R.id.textViewDevelopers)
        val getDevelopers = intent.getStringExtra("GAME_DEVELOPERS")
        if(getDevelopers.length > 3){
            var gamepageDevelopers = ""
            var toSplitString = getDevelopers!!.substring(0, getDevelopers.length-1)
            var toSplit = toSplitString.split("/")
            toSplit.forEach {
                var itemContents = it.split("|")
                gamepageDevelopers += itemContents.get(0) + " • "
            }
            developers.text = gamepageDevelopers.substring(0, gamepageDevelopers.length-2)
        }

        //putting the right age rating on the page
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


    //Method that sets what happens when menu button is clicked
    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val genres = findViewById<TextView>(R.id.game_genres)
        val getGenres = intent.getStringExtra("GAME_GENRES")
        if(getGenres.length > 3){
            genres.text = getGenres!!.substring(0, getGenres.length-2)
        }

        when(item.itemId){
            R.id.menuSave -> {
                //Passing game data to customize playlist dialog
                val game: Game2 = Game2(intent.getIntExtra("GAME_ID",0),
                    intent.getStringExtra("GAME_TITLE"), getGenres, intent.getStringExtra("GAME_COVER"),
                    intent.getStringExtra("GAME_DEVELOPERS"), intent.getStringExtra("GAME_CONSOLES"),
                    intent.getLongExtra("GAME_RD", 0), intent.getIntExtra("GAME_AGE_RATING", 0),
                    intent.getStringExtra("GAME_SUMMARY"), "")
                openCustomizePlaylistsDialog(game)
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

                            finish()
                            startActivity(intent)
                            overridePendingTransition(0,0)
                        }
                    }
                })

                alert.setNegativeButton("Leave", { dialogInterface: DialogInterface, i: Int -> })
                alert.show()

                return true}
            else -> return super.onOptionsItemSelected(item)
        }
    }

    //Opens customize dialog list
    private fun openCustomizePlaylistsDialog(game: Game2) {
        val customizePlaylistsDialog = CustomizePlaylistsDialog(game)
        customizePlaylistsDialog.show(supportFragmentManager, "customize playlist dialog")
    }



}
