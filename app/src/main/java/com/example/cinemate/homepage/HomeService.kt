package com.example.cinemate.homepage

import com.example.cinemate.searchpage.MovieResponse
import retrofit2.Call
import retrofit2.http.GET

interface HomeService {

    @GET("/movie/main")
    fun mainMovie(): Call<MovieResponse>
}