package com.example.cinemate.peoplepage

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface PeopleService {

    @GET("/connection")
    fun searchConnection(@Query("title") title:String, @Query("address") address:String, @Query("meatDate") meatDate:String): Call<ConnectionResponse>
}