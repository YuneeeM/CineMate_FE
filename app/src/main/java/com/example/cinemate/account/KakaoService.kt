package com.example.cinemate.account

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface KakaoService {
    @POST("/oauth/kakao")
    fun kakaoSignup(@Body params : KakaoSignupRequest) : Call<KakaoSignupResponse>
}