package com.example.cinemate.mypage

import com.google.gson.annotations.SerializedName

data class MypageResponse(
    @SerializedName("isSuccess")
    val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: MypageResult

)

data class MypageResult(
    @SerializedName("id") val id : String,
    @SerializedName("jwt") val jwt : String,
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String,
    @SerializedName("nickname") val nickname : String,
    @SerializedName("phone") val phone : String,
    @SerializedName("genre") val genre : String,
)


data class MovieLikeResponse(
    @SerializedName("isSuccess")
    val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: List<MovieLikeResult>

)

data class MovieLikeResult(
    @SerializedName("movieLikeId") val movieLikeId : String,

    @SerializedName("title") val title : String,
    @SerializedName("link") val link : String,
    @SerializedName("image") val image : String,
    @SerializedName("subtitle") val subtitle : String,
    @SerializedName("pubDate") val pubDate : String,
    @SerializedName("director") val director : String,
    @SerializedName("actor") val actor : String,
    @SerializedName("userRating") val usrRating : String,
    @SerializedName("genre") val genre : String,

    @SerializedName("nickname") val nickname : String,

)