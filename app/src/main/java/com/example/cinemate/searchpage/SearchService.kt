package com.example.cinemate.searchpage

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/movie")
    fun searchMovie(@Query("keyword") keyword: String): Call<MovieResponse>
}