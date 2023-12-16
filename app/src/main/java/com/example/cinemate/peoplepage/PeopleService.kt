package com.example.cinemate.peoplepage

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface PeopleService {

    @GET("/connection")
    fun searchConnection(@Query("title") title:String, @Query("address") address:String, @Query("meatDate") meatDate:String): Call<ConnectionResponse>

    @POST("/connection/add")
    fun postConnection(@Header("X-ACCESS-TOKEN") jwt: String, @Body params: ConnectionRequest) : Call<ConnectionResponse01>
}