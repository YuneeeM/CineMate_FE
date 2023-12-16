package com.example.cinemate.searchpage

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    @SerializedName("isSuccess")
    val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: List<Item>)

data class Item(
    @SerializedName("title") val title : String,
    @SerializedName("link") val link : String,
    @SerializedName("image") val image : String,
    @SerializedName("subtitle") val subtitle : String,
    @SerializedName("pubDate") val pubDate : String,
    @SerializedName("director") val director : String,
    @SerializedName("actor") val actor : String,
    @SerializedName("userRating") val usrRating : String,
    @SerializedName("genre") val genre : String

)