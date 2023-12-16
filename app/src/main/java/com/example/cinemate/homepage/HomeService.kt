package com.example.cinemate.homepage

import com.example.cinemate.searchpage.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface HomeService {

    @GET("/movie/main")
    fun mainMovie(): Call<MovieResponse>

    @GET("/movie/main/genre")
    fun getGenreConnection(
        @Header("X-ACCESS-TOKEN") jwt: String,
    ): Call<MovieResponse>
}