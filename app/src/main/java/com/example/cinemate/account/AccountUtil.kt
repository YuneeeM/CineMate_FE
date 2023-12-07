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

fun connectSignup (signupRequest: SignupRequest, checkComplete: (isComplete: Boolean) -> Unit) {
    //2. service 객체 생성
    retrofit.create(SignupService::class.java)
        .signUp(signupRequest)
        //4. 네트워크 통신
        .enqueue(object : Callback<SignupResponse> {
            override fun onResponse(call: Call<SignupResponse>, response: Response<SignupResponse>) {
                Log.d(TAG, "회원가입 결과 -------------------------------------------")
                Log.d(TAG, "onResponse: ${response.body().toString()}")

                if(response.body() != null) {
                    if (response.body()!!.success){
                        checkComplete(true)
                    }

                }
            }

            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                Log.d(TAG, "회원가입 결과 실패 -------------------------------------------")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
}

fun connectKakao (kakaoSignupRequest: KakaoSignupRequest,checkComplete:  (token: KakaoResult) -> Unit ) {
    //2. service 객체 생성
    retrofit.create(KakaoService::class.java)
        .kakaoSignup(kakaoSignupRequest)
        //4. 네트워크 통신
        .enqueue(object : Callback<KakaoSignupResponse> {
            override fun onResponse(call: Call<KakaoSignupResponse>, response: Response<KakaoSignupResponse>) {
                Log.d(TAG, "카카오 로그인 결과 -------------------------------------------")
                Log.d(TAG, "onResponse: ${response.body().toString()}")

                if(response.body()!!.result !=null) {
                    if (response.body()!!.success){
                        checkComplete(response.body()!!.result)
                    }

                }
            }

            override fun onFailure(call: Call<KakaoSignupResponse>, t: Throwable) {
                Log.d(TAG, "카카오 로그인 결과 실패 -------------------------------------------")
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
}

fun connectAuto (checkComplete:  (token: AutoLoginResult) -> Unit){
    //2. service 객체 생성
    retrofit.create(AutoLoginService::class.java)
        .autoLogin(xAccessToken = ApplicationClass.sharedPreferences.getString("jwt",""))
        //4. 네트워크 통신
        .enqueue(object : Callback<AutoLoginResponse> {
            override fun onResponse(call: Call<AutoLoginResponse>, response: Response<AutoLoginResponse>) {
                Log.d(TAG, "자동 로그인 결과 -------------------------------------------")
                Log.d(TAG, "onResponse: ${response.body().toString()}")

                if(response.body()!!.result !=null) {
                    if (response.body()!!.success){
                        checkComplete(response.body()!!.result)
                    }

                }
            }

            override fun onFailure(call: Call<AutoLoginResponse>, t: Throwable) {
                Log.d(TAG, "자동 로그인 결과 실패 -------------------------------------------")
                Log.e(TAG, "onFailure: ${t.message}")

            }
        })
}