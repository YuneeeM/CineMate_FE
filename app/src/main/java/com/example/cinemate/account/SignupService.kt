package com.example.cinemate.account

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface SignupService {
    @POST("users")
    fun signUp(@Body params: SignupRequest):Call<SignupResponse>
}