package com.example.cinemate.account

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AutoLoginService {
    @POST("/users/autologin")
    fun autoLogin(@Header("X-ACCESS-TOKEN") xAccessToken: String): Call<AutoLoginResponse>
}