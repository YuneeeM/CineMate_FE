package com.example.cinemate.mypage

import com.example.cinemate.peoplepage.ConnectionResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface MypageService {

    @GET("/mypage")
    fun getMypage( @Header("X-ACCESS-TOKEN") jwt: String):Call<MypageResponse>

    @GET("/likes/all-movie")
    fun getLikesMovie(@Header("X-ACCESS-TOKEN") jwt: String):Call<MovieLikeResponse>

    @POST("/likes/movie/add")
    fun postLikeMovie(@Header("X-ACCESS-TOKEN") jwt: String,@Query("title") title: String):Call<MovieLikeResponse>

    @GET("/connection/user")
    fun getConnectionUser(@Header("X-ACCESS-TOKEN") jwt: String):Call<ConnectionResponse>
}