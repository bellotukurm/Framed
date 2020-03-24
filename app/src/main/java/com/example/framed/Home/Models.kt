package com.example.framed.Home

class Game(val id: Int, val name: String, val genres: List<Genre>, val cover: Cover,
           val involved_companies: List<InvolvedCompany>, val platforms: List<Platform>,
           val first_release_date: Long, val age_ratings: List<AgeRating>,
           val summary: String, val screenshots: List<Screenshot>, var videos: List<Video>)

class Game2(val id: Int, val name: String, val genres: String, val cover: String,
            val involved_companies: String, val platforms: String, val first_release_date: Long,
            val age_ratings: Int, val summary: String)

class Game3(val name: String, val genres: String, val cover: String,
            val involved_companies: String, val platforms: String, val first_release_date: Long,
            val age_ratings: Int, val summary: String)

class Cover(val id: Int, val url: String)

class Genre(val id: Int, val name: String)

class InvolvedCompany(val id: Int, val company: Company)

class Company(val id: Int, val name: String)

class Platform(val id: Int, val name: String)

class AgeRating(val id: Int, val rating: Int)

class Screenshot(val id: Int, val url: String)

class Video(val id: Int, val video_id: String)

