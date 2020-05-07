package com.example.framed.Utils

import android.app.Activity
import android.content.Intent
import com.example.framed.Pages.GamePage
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.lang.reflect.Type

class Game(val id: Int, val name: String, val genres: List<Genre>, val cover: Cover,
           val involved_companies: List<InvolvedCompany>, val platforms: List<Platform>,
           val first_release_date: Long, val age_ratings: List<AgeRating>,
           val summary: String, val screenshots: List<Screenshot>, var videos: List<Video>,
           val playlists: String)

class Game2(val id: Int, val name: String, val genres: String, val cover: String,
            val involved_companies: String, val platforms: String, val first_release_date: Long,
            val age_ratings: Int, val summary: String, var playlists: String)

class Game3(val name: String, val genres: String, val cover: String,
            val involved_companies: String, val platforms: String, val first_release_date: Long,
            val age_ratings: Int, val summary: String, val playlists: String)

class Cover(val id: Int, val url: String)

class Genre(val id: Int, val name: String)

class InvolvedCompany(val id: Int, val company: Company)

class Company(val id: Int, val name: String)

class Platform(val id: Int, val name: String)

class AgeRating(val id: Int, val rating: Int)

class Screenshot(val id: Int, val url: String)

class Video(val id: Int, val video_id: String)

class Playlist(val id: Int, val name: String)

class PlatformRecurrence(val name: String, var recurrence: Int = 1)

class AgeRatingRecurrence(val id: Int, var recurrence: Int = 1)

class GenreRecurrence(val name: String, var recurrence: Int = 1)

class DeveloperRecurrence(val name: String, var recurrence: Int = 1)

fun getPlatformRecurrence(dbList: List<Game2>): List<PlatformRecurrence>{
    var uniqueConsoles: MutableList<String> = ArrayList()
    var recConsoles: MutableList<PlatformRecurrence> = ArrayList()

    //for each game in database
    dbList.forEach{
        //get all platforms as string and split them based on "/"
        var toSplitString = it.platforms.substring(0, it.platforms.length-1)
        var toSplit = toSplitString.split("/")

        //for each platform of a game
        toSplit.forEach{
            //if platform already in list add 1 to number of recurrence else put it in list
            if(uniqueConsoles.contains(it)  || uniqueConsoles.contains(it+" ") || uniqueConsoles.contains(it.substring(0, it.length-1))){
                val zname = it
                recConsoles.forEach {
                    if(it.name.contains(zname)){
                        it.recurrence = it.recurrence+1
                    }
                }
            }
            else{
                uniqueConsoles.add(it)
                recConsoles.add(PlatformRecurrence(it))
            }
        }
    }
    //returning the list of unique platforms and how many times they occur
    return recConsoles
}

fun getAgeRatingsRecurrence(dbList: List<Game2>): List<AgeRatingRecurrence>{

    var uniqueAgeRatings: MutableList<Int> = ArrayList()
    var recAgeRating: MutableList<AgeRatingRecurrence> = ArrayList()
    dbList.forEach{
        if(uniqueAgeRatings.contains(it.age_ratings)){
            val zrating = it.age_ratings
            recAgeRating.forEach {
                if(it.id == zrating){
                    it.recurrence = it.recurrence+1
                }
            }
        }else{
            uniqueAgeRatings.add(it.age_ratings)
            recAgeRating.add(AgeRatingRecurrence(it.age_ratings))
        }
    }
    return recAgeRating
}

fun getGenreRecurrence(dbList: List<Game2>): List<GenreRecurrence>{
    var uniqueGenres: MutableList<String> = ArrayList()
    var recGenres: MutableList<GenreRecurrence> = ArrayList()
    dbList.forEach{
        var toSplitString = it.genres.substring(0, it.genres.length-1)
        var toSplit = toSplitString.split("/")
        toSplit.forEach{
            if(uniqueGenres.contains(it) || uniqueGenres.contains(it+" ") || uniqueGenres.contains(it.substring(0, it.length-1))){
                val zname = it
                recGenres.forEach {
                    if(it.name.contains(zname)){
                        it.recurrence = it.recurrence+1
                    }
                }
            }
            else{
                uniqueGenres.add(it)
                recGenres.add(GenreRecurrence(it))
            }
        }
    }
    return recGenres
}

fun getDeveloperRecurrence(dbList: List<Game2>): List<DeveloperRecurrence>{
    var uniqueDevelopers: MutableList<String> = ArrayList()
    var recDevelopers: MutableList<DeveloperRecurrence> = ArrayList()
    dbList.forEach{
        var toSplitString = it.involved_companies.substring(0, it.involved_companies.length-1)
        var toSplit = toSplitString.split("/")
        toSplit.forEach{
            if(uniqueDevelopers.contains(it) || uniqueDevelopers.contains(it+" ") || uniqueDevelopers.contains(it.substring(0, it.length-1))){
                val zname = it
                recDevelopers.forEach {
                    if(it.name.contains(zname)){
                        it.recurrence = it.recurrence+1
                    }
                }
            }
            else{
                uniqueDevelopers.add(it)
                recDevelopers.add(DeveloperRecurrence(it))
            }
        }
    }
    return recDevelopers
}


