package com.example.cinemate.peoplepage

import com.google.gson.annotations.SerializedName
import java.sql.Timestamp
import java.time.LocalDate

data class ConnectionRequest(
    @SerializedName("title") val title: String,
    @SerializedName("address") val address: String,
    @SerializedName("theather") val theather: String,
    @SerializedName("meatDate") val meatDate: String,
    @SerializedName("sentence") val sentence: String,
)


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

data class ConnectionResponse01(
    @SerializedName("isSuccess")
    val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: ConnectionResult

)

data class ConnectionResult(
    @SerializedName("title") val title: String,
    @SerializedName("image") val image: String,
    @SerializedName("connectionId") val connectionId: String,
    @SerializedName("theather") val theather: String,
    @SerializedName("address") val address: String,
    @SerializedName("sentence") val sentence: String,
    @SerializedName("createAt") val createAt: String,
    @SerializedName("meatDate") val meatDate: String,
    @SerializedName("nickname") val nickname: String
)


