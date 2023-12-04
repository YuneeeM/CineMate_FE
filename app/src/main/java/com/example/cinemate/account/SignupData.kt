package com.example.cinemate.account

import com.google.gson.annotations.SerializedName

data class SignupRequest (
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String,
    @SerializedName("nickname") val nickname: String,
    @SerializedName("phone") val phone : String,
    @SerializedName("genere") val genere : String

)

data class SignupResponse (
    @SerializedName("isSuccess")
    val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: SignupResult

)

data class SignupResult (
    @SerializedName("id") val id : Long,
    @SerializedName("jwt") val jwt: String?,
    @SerializedName("nickname") val nickname: String
)

