package com.example.cinemate.account

import com.google.gson.annotations.SerializedName

data class KakaoSignupRequest (
    @SerializedName("access_token") val access_token:String
)

data class KakaoSignupResponse (
    @SerializedName("isSuccess") val success: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: KakaoResult

)

data class KakaoResult (
    @SerializedName("id") val id : Long,
    @SerializedName("jwt") val jwt: String?,
)
