package com.example.cinemate.account

import com.google.gson.annotations.SerializedName

data class AutoLoginRequest (
    @SerializedName("id") val id : Long,

)

data class AutoLoginResponse (
    @SerializedName("isSuccess")
    val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: AutoLoginResult

)

data class AutoLoginResult (
    @SerializedName("id") val id : Long,
    @SerializedName("jwt") val jwt: String?,
    @SerializedName("nickname") val nickname: String
)

