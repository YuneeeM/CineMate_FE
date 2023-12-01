package com.example.cinemate.account

import com.google.gson.annotations.SerializedName

data class LoginRequest (
    @SerializedName("email")
    val email: String, //이메일

    @SerializedName("password")
    val password: String
)

data class LoginResponse (
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("code")
    val code: Int,

    @SerializedName("message")
    val message: String,

    @SerializedName("result")
    val result: LoginResult

)

data class LoginResult (
    @SerializedName("id") val id : Long,
    @SerializedName("jwt") val jwt: String?
)
