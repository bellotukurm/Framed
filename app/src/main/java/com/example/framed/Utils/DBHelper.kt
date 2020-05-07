package com.example.framed.Utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_GAMES_TABLE =
            "CREATE TABLE $TABLE_GAMES($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_GAME_TITLE TEXT," +
                    "$COLUMN_GAME_GENRES TEXT, $COLUMN_GAME_COVER TEXT, $COLUMN_GAME_INVOLVED_COMPANIES TEXT," +
                    "$COLUMN_GAME_PLATFORMS TEXT, $COLUMN_GAME_RELEASE_DATE LONG, $COLUMN_GAME_AGE_RATINGS INT," +
                    "$COLUMN_GAME_SUMMARY TEXT, $COLUMN_GAME_PLAYLISTS TEXT)"

        val CREATE_PLAYLISTS_TABLE =
            "CREATE TABLE $TABLE_PLAYLISTS($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_PLAYLISTS_TITLE TEXT)"

        db?.execSQL(CREATE_GAMES_TABLE)
        db?.execSQL(CREATE_PLAYLISTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_GAMES")
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_PLAYLISTS")
        onCreate(db)
    }

    fun addGame(game: Game3) {
        val values = ContentValues()
        values.put(COLUMN_GAME_TITLE, game.name)
        values.put(COLUMN_GAME_GENRES, game.genres)
        values.put(COLUMN_GAME_COVER, game.cover)
        values.put(COLUMN_GAME_INVOLVED_COMPANIES, game.involved_companies)
        values.put(COLUMN_GAME_PLATFORMS, game.platforms)
        values.put(COLUMN_GAME_RELEASE_DATE, game.first_release_date)
        values.put(COLUMN_GAME_AGE_RATINGS, game.age_ratings)
        values.put(COLUMN_GAME_SUMMARY, game.summary)
        values.put(COLUMN_GAME_PLAYLISTS, game.playlists)

        val db = this.writableDatabase
        db.insert(TABLE_GAMES, null, values)
    }

    fun updateGamePlaylists(game: Game2): Int{
        val values = ContentValues()
        values.put(COLUMN_GAME_PLAYLISTS, game.playlists)
        val db = this.writableDatabase

        return db.update(
            TABLE_GAMES,
            values,
            "$COLUMN_ID =?",
            arrayOf((game.id).toString()))
    }

    fun readGames(): List<Game2> {
        val sql = "select * from $TABLE_GAMES"
        val db = this.readableDatabase
        var storeGames = arrayListOf<Game2>()
        val cursor = db.rawQuery(sql,null)
        if(cursor.moveToFirst()){
            do{
                val id = Integer.parseInt(cursor.getString(0))
                val name = cursor.getString(1)
                val genres = cursor.getString(2)
                val cover = cursor.getString(3)
                val involved_companies = cursor.getString(4)
                val platforms = cursor.getString(5)
                val first_release_date = cursor.getLong(6)
                val age_ratings = cursor.getInt(7)
                val summary = cursor.getString(8)
                val playlists = cursor.getString(9)

                storeGames.add(
                    Game2(
                        id, name, genres, cover, involved_companies, platforms,
                        first_release_date, age_ratings, summary, playlists
                    )
                )

            }while(cursor.moveToNext())
        }
        cursor.close()
        return storeGames
    }

    fun countGames(): Int {
        val countQuery = "SELECT  * FROM $TABLE_GAMES"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(countQuery, null)
        val count: Int = cursor.getCount()
        cursor.close()
        return count
    }

    fun deleteGame(id: Int){
        val db = this.writableDatabase
        db.delete(TABLE_GAMES,"$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    fun countPlaylists(): Int {
        val countQuery = "SELECT  * FROM $TABLE_PLAYLISTS"
        val db = this.readableDatabase
        val cursor: Cursor = db.rawQuery(countQuery, null)
        val count: Int = cursor.getCount()
        cursor.close()
        return count
    }

    fun addPlaylist(playlistName: String) {
        val values = ContentValues()
        values.put(COLUMN_PLAYLISTS_TITLE, playlistName)
        val db = this.writableDatabase
        db.insert(TABLE_PLAYLISTS, null, values)
    }

    fun readPlaylists(): List<Playlist> {
        val sql = "select * from $TABLE_PLAYLISTS"
        val db = this.readableDatabase
        //var storePlaylistItems = arrayListOf<PlaylistItem>()
        var storePlaylists = arrayListOf<Playlist>()
        val cursor = db.rawQuery(sql,null)
        if(cursor.moveToFirst()){
            do{
                val id = Integer.parseInt(cursor.getString(0))
                val name = cursor.getString(1)

                storePlaylists.add(
                    Playlist(id, name)
                )
            }while(cursor.moveToNext())
        }
        /*var uniquePlaylistNames: MutableList<String> = ArrayList()
        storePlaylistItems.forEach {
            val curGame = it.game
            if (uniquePlaylistNames.contains(it.name)) {
                val uniqueName = it.name
                storePlaylists.forEach {
                    if (uniqueName == it.name) {
                        it.games.add(curGame)
                    }
                }
            } else {
                uniquePlaylistNames.add(it.name)
                val games: MutableList<Int> = ArrayList()
                games.add(it.game)
                storePlaylists.add(Playlist(it.name, games))
            }
        }*/
        cursor.close()
        return storePlaylists
    }

    fun deletePlaylist(id: Int){
        val db = this.writableDatabase
        db.delete(TABLE_PLAYLISTS,"$COLUMN_ID = ?", arrayOf(id.toString()))
    }

    companion object {
        private val DATABASE_VERSION = 5
        private val DATABASE_NAME = "game"
        private val TABLE_GAMES = "games"
        private val TABLE_PLAYLISTS = "playlists"
        private val COLUMN_PLAYLISTS_TITLE = "playlistsname"
        private val COLUMN_GAME_PLAYLISTS = "playlists"
        private val COLUMN_ID = "_id"
        private val COLUMN_GAME_TITLE = "gamename"
        private val COLUMN_GAME_GENRES = "genres"
        private val COLUMN_GAME_COVER = "cover"
        private val COLUMN_GAME_INVOLVED_COMPANIES = "involved_companies"
        private val COLUMN_GAME_PLATFORMS = "platforms"
        private val COLUMN_GAME_RELEASE_DATE = "release_date"
        private val COLUMN_GAME_AGE_RATINGS = "age_ratings"
        private val COLUMN_GAME_SUMMARY = "summary"
    }
}
