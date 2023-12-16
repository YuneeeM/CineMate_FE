package com.example.cinemate.mypage

import com.example.cinemate.peoplepage.ConnectionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface MypageService {

    @GET("/mypage")
    fun getMypage(@Header("X-ACCESS-TOKEN") jwt: String): Call<MypageResponse>

    @GET("/likes/all-movie")
    fun getLikesMovie(@Header("X-ACCESS-TOKEN") jwt: String): Call<MovieLikeResponse>

    @POST("/likes/movie/add")
    fun postLikeMovie(
        @Header("X-ACCESS-TOKEN") jwt: String,
        @Query("title") title: String
    ): Call<MovieLikeResponse01>

    @GET("/connection/user")
    fun getConnectionUser(@Header("X-ACCESS-TOKEN") jwt: String): Call<ConnectionResponse>

    @GET("/clikes/all-connect")
    fun getLikesConnection(@Header("X-ACCESS-TOKEN") jwt: String): Call<CResponse>

    @POST("/clikes/add/{connectionId}")
    fun postLikeConnection(
        @Header("X-ACCESS-TOKEN") jwt: String,
        @Path("connectionId") connectionId: Long
    ): Call<CResponse01>


}