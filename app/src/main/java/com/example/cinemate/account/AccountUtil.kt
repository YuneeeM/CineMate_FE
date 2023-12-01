package com.example.cinemate.account

import android.content.ContentValues.TAG
import android.util.Log
import com.example.cinemate.ApplicationClass
import com.example.cinemate.retrofitUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

//api 호출
//1. retrofit 객체 생성
private val retrofit : Retrofit = retrofitUtil.getInstance()

private var jwtTokenValue : String ?= null

fun setJwt() {
    jwtTokenValue = ApplicationClass.sharedPreferences.getString("jwt","")
}

fun connectLogin(loginRequest: LoginRequest, checkComplete : (token: LoginResult) -> Unit) {
    //2. service 객체 생성
    retrofit.create(LoginService::class.java)
        .login(loginRequest)
        //4. 네트워크 통신
        .enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d(TAG, "로그인 결과 -------------------------------------------")
                Log.d(TAG, "onResponse: ${response.body().toString()}")

                if(response.body() != null) {
                    if (response.body()!!.result !=null){
                        checkComplete(response.body()!!.result)
                    }

                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.d(TAG, "로그인 결과 실패 -------------------------------------------")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
}