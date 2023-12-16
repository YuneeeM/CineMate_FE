package com.example.cinemate.peoplepage

import org.intellij.lang.annotations.JdkConstants.PatternFlags
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PeopleService {

    @GET("/connection")
    fun searchConnection(@Query("title") title:String, @Query("address") address:String, @Query("meatDate") meatDate:String): Call<ConnectionResponse>

    @POST("/connection/add")
    fun postConnection(@Header("X-ACCESS-TOKEN") jwt: String, @Body params: ConnectionRequest) : Call<ConnectionResponse01>

    @GET("/connection/{connectionId}")
    fun getIdConnection(@Path("connectionId") connectionId : Long) : Call<ConnectionResponse01>

    @POST("/comment/add/{connectionId}")
    fun postComment(@Header("X-ACCESS-TOKEN") jwt: String,@Path("connectionId") connectionId : Long,@Query("body") body:String) : Call<CommentResponse01>

    @GET("/comment/{connectionId}")
    fun getIdComment(@Path("connectionId") connectionId : Long) : Call<CommentResponse>


}