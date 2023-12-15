package com.example.cinemate.peoplepage

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.time.LocalDate

data class ConnectionResponse(
    @SerializedName("isSuccess")
    val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: List<ConnectionResult>
)
data class ConnectionResult (
    @SerializedName("title") val title : String,
    @SerializedName("image") val image : String,
    @SerializedName("connectionId") val connectionId : Long,
    @SerializedName("theather") val theather : String,
    @SerializedName("address") val address : String,
    @SerializedName("sentence") val sentence : String,
    @SerializedName("createAt") val createAt : String,
    @SerializedName("meatDate") val meatDate : String,

    )